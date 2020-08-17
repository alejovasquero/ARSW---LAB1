/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import edu.eci.arsw.threads.BlackListThread;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress, int threads){
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        int checkedListsCount=0;
        int n = skds.getRegisteredServersCount()-1;
        List<BlackListThread> tr = createCheckThreads(n, threads, ipaddress);
        solveWithThreads(tr);
        List<Integer> serversFound = joinOcurrences(tr);
        checkedListsCount = BlackListThread.listsChecked.get();
        int ocurrencesCount=serversFound.size();
        if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        return serversFound;
    }
    


    private List<BlackListThread> createCheckThreads(int servers, int threads, String ipaddress){
        List<BlackListThread> tr = new ArrayList<BlackListThread>();
        int n = servers;
        if(threads == 1){
            tr.add(new BlackListThread(0, n ,ipaddress));
        } else if(threads == 2){
            tr.add(new BlackListThread(0, n/2, ipaddress));
            tr.add(new BlackListThread( n/2 +1, n, ipaddress));
        } else if (threads > 2) {
            tr.add(new BlackListThread(0, n/threads, ipaddress));
            for(int i=1; i<threads-1; i++){
                tr.add(new BlackListThread(i*(n/threads)+1, (i+1)*(n/threads), ipaddress));
            }
            tr.add(new BlackListThread( (threads-1)*(n/threads) +1, n, ipaddress));
        }
        return tr;
    }

    private void solveWithThreads(List<BlackListThread> list){
        BlackListThread.listsChecked.set(0);
        for(BlackListThread t: list){
            t.start();
        }
        for(BlackListThread t: list){
            try {
                t.join();
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
    }

    private List<Integer> joinOcurrences(List<BlackListThread> list){
        ArrayList<Integer> ans = new ArrayList<Integer>();
        for(BlackListThread t: list){
            ans.addAll(t.getList());
        }
        return ans;
    }

    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    public static int getBlackListAlarmCount(){
        return BLACK_LIST_ALARM_COUNT;
    }
    
}
