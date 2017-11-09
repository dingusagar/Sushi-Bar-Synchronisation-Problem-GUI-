/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushi;

import GUI.mainForm;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dingu
 */
public class SushiBar {
    
    private int eating = 0,waiting = 0;
    private boolean mustWait = false;
    private Semaphore block,mutex;
    
    
    private Thread[] threads;
    private final int NoOfThreads = 12;

    public SushiBar() {
           
        block = new Semaphore(0,true);
        mutex = new Semaphore(1,true);
        threads = new Thread[NoOfThreads];
    }
    
    public void acquire_sem_mutex()
    {
        try {
            mutex.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(SushiBar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void acquire_sem_block()
    {
        try {
            block.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(SushiBar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void release_sem_mutex()
    {
        mutex.release();
    }
    
    public void release_sem_block()
    {
        block.release();
    }
    
    
    public void setMustWait(boolean val)
    {
        mustWait = val;
    }
    
    public boolean getMustWait()
    {
        return mustWait;
    }

    public int getEating() {
        return eating;
    }

    public void setEating(int eating) {
        this.eating = eating;
    }

    public int getWaiting() {
        return waiting;
    }

    public void setWaiting(int waiting) {
        this.waiting = waiting;
    }

    public int getNoOfThreads() {
        return NoOfThreads;
    }

    
    public  void startTheSushiBar(mainForm form) throws InterruptedException
    {
        for(int i =0; i< NoOfThreads ;i++)
        {
            threads[i] = new Thread(new SushiRunner(""+i,this,form));
            threads[i].start();
            Thread.sleep(1000);
        }
    }
    
    
}
