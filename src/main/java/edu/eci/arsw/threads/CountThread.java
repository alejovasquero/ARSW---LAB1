/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThread extends Thread{
    private int left;
    private int right;
    public CountThread(int left, int right){
        super();
        this.left=left;
        this.right=right;
    }

    @Override
    public void run() {
        for(int i=left; i<right ;i++){
            System.out.println(i);
        }
    }
}
