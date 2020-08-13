/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import edu.eci.arsw.threads.BlackListThread;

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
        
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();
        
        int ocurrencesCount=0;
        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        
        int checkedListsCount=0;
        int n = skds.getRegisteredServersCount()-1;

        BlackListThread.ocurrences.set(0);
        BlackListThread.listsChecked.set(0);
        ArrayList<BlackListThread> tr = new ArrayList<BlackListThread>();
        if(threads == 1){
            //Run thread (0, n)
            BlackListThread one = new BlackListThread(0, n ,ipaddress);
            tr.add(one);
            one.start();
        } else if(threads == 2){
            //Run thread (0, ndiv2) (ndiv2 +1 , n)
            BlackListThread one = new BlackListThread(0, n/2, ipaddress);
            BlackListThread two = new BlackListThread( n/2 +1, n, ipaddress);
            tr.add(one);
            tr.add(two);
            one.start();
            two.start();
        } else if (threads > 2) {
            //Run thread (0, ndivt) ( (threads-1) (ndiv2) +1 , n)
            BlackListThread one = new BlackListThread(0, n/threads, ipaddress);
            BlackListThread two = new BlackListThread( (threads-1)*(n/threads) +1, n, ipaddress);
            tr.add(one);
            tr.add(two);
            one.start();
            two.start();
            BlackListThread t = null;
            for(int i=1; i<threads-1; i++){
                t = new BlackListThread(i*(n/threads)+1, (i+1)*(n/threads), ipaddress);
                tr.add(t);
                t.start();
                //Run thread (i*(n div t) +1 , (i+1)*(n div t))
            }
        }

        for(BlackListThread t : tr){
            try {
                t.join();
            } catch (InterruptedException e) {

            }
        }
        System.out.println("All threds done...");
        System.out.println(BlackListThread.ocurrences.get());
        System.out.println(BlackListThread.listsChecked.get());
        ocurrencesCount = BlackListThread.ocurrences.get();
        
        if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }
        System.out.println(BlackListThread.listsChecked.get());
        System.out.println(checkedListsCount);
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }
    
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    public static int getBlackListAlarmCount(){
        return BLACK_LIST_ALARM_COUNT;
    }
    
}
