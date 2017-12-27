package com.jackzhang.concur.countdown;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * 主线程阻塞等待所有子线程执行完毕
 * Created by Jack.
 */
public class CountDownTest {
    @Test
    public void await(){
        final CountDownLatch latch=new CountDownLatch(2);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.currentThread().sleep(2000);
                    System.out.println("线程"+Thread.currentThread().getName()+"执行完毕");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.currentThread().sleep(3000);
                    System.out.println("线程"+Thread.currentThread().getName()+"执行完毕");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有线程执行完毕");

    }
}
