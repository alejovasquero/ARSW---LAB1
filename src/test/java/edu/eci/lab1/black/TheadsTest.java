package edu.eci.lab1.black;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.eci.arsw.threads.CountThread;

public class TheadsTest {
    
    @Test
    public void shouldCreateThread(){
        Thread t1 = new CountThread(0, 99);
        t1.start();
        assertTrue(t1.isAlive());
    }

    @Test
    public void shouldRunThread(){
        Thread t1 = new CountThread(0, 99);
        t1.run();
        assertFalse(t1.isAlive());
    }
}