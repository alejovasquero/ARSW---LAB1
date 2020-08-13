package edu.eci.arsw.threads;

import edu.eci.arsw.blacklistvalidator.HostBlackListsValidator;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class BlackListThread extends Thread{
    private int left;
    private int right;
    private String ip;
    public static AtomicInteger listsChecked = new AtomicInteger();
    public static AtomicInteger ocurrences = new AtomicInteger();

    public BlackListThread(int left, int right, String ipAddress){
        this.left = left;
        this.right = right;
        this.ip = ipAddress;
    }


    @Override
    public void run() {
        System.out.printf("%d %d\n", left, right);
        ArrayList<Integer> lists = new ArrayList<Integer>();
        for(int i = left; i<=right && ocurrences.get() < HostBlackListsValidator.getBlackListAlarmCount() ; i++){
            listsChecked.incrementAndGet();
            if (HostBlacklistsDataSourceFacade.getInstance().isInBlackListServer(i, ip)){
                lists.add(i);
                ocurrences.incrementAndGet();

            }
        }
        System.out.println("I am done...");
    }
}
