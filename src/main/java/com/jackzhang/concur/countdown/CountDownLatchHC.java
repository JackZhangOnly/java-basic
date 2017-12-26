package com.jackzhang.concur.countdown;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 模拟高并发HTTP请求
 * Created by Jack
 */
public class CountDownLatchHC {
    private final int THREAD_NNUM=100;
    private CountDownLatch downLatch=new CountDownLatch(THREAD_NNUM);

    @Test
    public void runHttp() throws InterruptedException {
        for (int i=0;i<THREAD_NNUM;i++){
            new Thread(new HttpReq(downLatch)).start();
            //数减1，为0时放开所有阻塞线程
            downLatch.countDown();
        }

        Thread.currentThread().sleep(2000);
    }

     class HttpReq implements Runnable{
        private CountDownLatch latch;
        public HttpReq(CountDownLatch downLatch){
            this.latch=downLatch;
        }
        public void run() {
            try {
                latch.await();//阻塞等待当前线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("我要发送请求了-----"+Thread.currentThread().getName()+"时间"+new Date().getTime());
        }
    }
}
