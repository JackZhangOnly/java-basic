package com.jackzhang.concur.cyclicb;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 一组线程到达屏障点时，优先执行barrierAction
 * Created by Jack
 */
public class CyclicBarrierAction {
    @Test
    public void testAction(){
        final CyclicBarrier cyclicBarrier=new CyclicBarrier(2,new Action());

        new Thread(new Runnable() {
            public void run() {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(1);
            }
        }).start();
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("main");


    }
    class Action implements Runnable{

        public void run() {
            System.out.println("Action");
        }
    }
}
