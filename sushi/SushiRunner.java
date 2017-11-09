/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushi;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import GUI.mainForm;

/**
 *
 * @author dingu
 */
public class SushiRunner implements Runnable{

    private String threadName;
    private SushiBar sushibar;
    private Random randGenerator;
    private Semaphore block,mutex;
    private mainForm form;
   
    
    public SushiRunner(String threadName,SushiBar sushibar,mainForm form) {
        this.form = form;
        this.threadName = threadName;
        this.sushibar = sushibar;
        randGenerator = new Random();
        this.sushibar = sushibar;
    }

    
    @Override
    public void run() {
        try {
            Thread.sleep(randGenerator.nextInt(100));
        } catch (InterruptedException ex) {
            Logger.getLogger(SushiRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sushibar.acquire_sem_mutex();
        
        
    
        
    if(sushibar.getMustWait())
    {      
        sushibar.setWaiting(sushibar.getWaiting() + 1 );
        System.out.println("Waiting customer : " + threadName);
            try {
                form.addPersonWaiting();
            } catch (InterruptedException ex) {
                Logger.getLogger(SushiRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        sushibar.release_sem_mutex();     
        sushibar.acquire_sem_block();
        
    }else
    {
       sushibar.setEating(sushibar.getEating() + 1);
       sushibar.setMustWait(sushibar.getEating() == 5);
       sushibar.release_sem_mutex();
       
    }
//    Eating time...
    System.out.println("Eating Customer : " + threadName);
    form.removePersonWaiting();
        try {
            form.addCustomerToTable();
        } catch (InterruptedException ex) {
            Logger.getLogger(SushiRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SushiRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    sushibar.acquire_sem_mutex();
    sushibar.setEating(sushibar.getEating() - 1);
    
    System.out.println("Leaving Customer : " + threadName);
    form.removeCustomerFromTable();
    
    if(sushibar.getEating() == 0 && sushibar.getWaiting() !=0 )
    {
        int n = Math.min(5,sushibar.getWaiting());
        sushibar.setWaiting(sushibar.getWaiting() - n);
        sushibar.setEating(sushibar.getEating() + n);
        sushibar.setMustWait(sushibar.getEating() == 5);
        
        for(int i=0;i<n;i++)
        {
            sushibar.release_sem_block();
        }
    }
    
    sushibar.release_sem_mutex();

    
        
        
        
    
    
        
        
        
        
    }
    
}
