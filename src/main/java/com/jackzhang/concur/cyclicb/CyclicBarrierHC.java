package com.jackzhang.concur.cyclicb;

import com.jackzhang.concur.countdown.CountDownLatchHC;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 模拟高并发HTTP请求
 * CountDownLatch的计数器只能使用一次，CyclicBarrier的可以使用reset()方法重置
 * 所以CyclicBarrier能处理更为复杂的业务场景。如计算出错时，可以重置计数器并让线程重新执行一次；
 * CyclicBarrier提供了一些可用的方法，如getNumberWaiting方法可以获得阻塞线程的数量
 * isBroken()方法可以判定阻塞的线程是否被中断
 * Created by Jack
 */
public class CyclicBarrierHC {

    private final int THREAD_NNUM=100;
    private CyclicBarrier cyclicBarrier=new CyclicBarrier(THREAD_NNUM);

    @Test
    public void runHttp() throws InterruptedException {
        for (int i=0;i<THREAD_NNUM;i++){
            new Thread(new HttpReq(cyclicBarrier)).start();
        }
        Thread.currentThread().sleep(4000);
    }

    class HttpReq implements Runnable{
        private CyclicBarrier cyclicBarrier;
        public HttpReq(CyclicBarrier barrier){
            this.cyclicBarrier=barrier;
        }
        public void run() {
            try {
                Thread.currentThread().sleep(2000);
                cyclicBarrier.await();//阻塞等待当前线程，直到所有的线程到达屏障点（即await时）
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("我要发送请求了-----"+Thread.currentThread().getName()+"时间"+new Date().getTime());
        }
    }
}
