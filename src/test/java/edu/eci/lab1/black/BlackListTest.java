package edu.eci.lab1.black;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import edu.eci.arsw.blacklistvalidator.HostBlackListsValidator;
import edu.eci.arsw.threads.BlackListThread;


public class BlackListTest {

    @Test
    public void shouldCheckBlackListTrustworthy(){
        HostBlackListsValidator hblv=new HostBlackListsValidator();
        List<Integer> blackListOcurrences=hblv.checkHost("205.24.34.55", 50);
        assertEquals(blackListOcurrences.size(),0);
    }

    @Test
    public void shouldCheckBlackListTrustworthy2(){
        HostBlackListsValidator hblv=new HostBlackListsValidator();
        List<Integer> blackListOcurrences=hblv.checkHost("127.0.0.1", 50);
        assertEquals(blackListOcurrences.size(),0);
    }

    @Test
    public void shouldCheckBlackListNotTrustworthy(){
        HostBlackListsValidator hblv=new HostBlackListsValidator();
        List<Integer> blackListOcurrences=hblv.checkHost("202.24.34.55", 50);
        assertEquals(blackListOcurrences.size(),5);
    }
    @Test
    public void shouldCheckBlackListNotTrustworthy2(){
        HostBlackListsValidator hblv=new HostBlackListsValidator();
        List<Integer> blackListOcurrences=hblv.checkHost("200.24.34.55", 50);
        assertEquals(blackListOcurrences.size(),5);
    }

    @Test
    public void shouldCreateBlackListThread(){
        BlackListThread t1 = new BlackListThread(0, 99, "205.24.34.55");
        t1.start();
        assertTrue(t1.isAlive());
    }

    @Test
    public void shouldRunBlackListThread(){
        BlackListThread t1 = new BlackListThread(0, 99, "205.24.34.55");
        t1.run();
        assertFalse(t1.isAlive());
    }
}