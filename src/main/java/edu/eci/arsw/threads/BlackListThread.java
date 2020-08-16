package edu.eci.arsw.threads;

import edu.eci.arsw.blacklistvalidator.HostBlackListsValidator;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class BlackListThread extends Thread{
    private int left;
    private int right;
    private String ip;
    public static AtomicInteger listsChecked = new AtomicInteger(0);
    private ArrayList<Integer> list;

    public BlackListThread(int left, int right, String ipAddress){
        this.left = left;
        this.right = right;
        this.ip = ipAddress;
    }


    @Override
    public void run() {
        list = new ArrayList<Integer>();
        boolean follow = true;
        boolean isDanger;
        for(int i = left; i<=right; i++){
            isDanger = HostBlacklistsDataSourceFacade.getInstance().isInBlackListServer(i, ip);
            listsChecked.incrementAndGet();
            if(isDanger){
                list.add(i);
            }
        }
    }

    public ArrayList<Integer> getList(){
        return list;
    }
}
