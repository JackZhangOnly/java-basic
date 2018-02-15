package com.jackzhang.concur.exchanger;

import org.junit.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  Exchanger(交换者)用于线程间协作的工具类，用于线程间数据交换
 *  它提供了一个同步点，在这个同步点，两个线程可以交换彼此的数据
 *  线程1先执行exchange()方法，会一直等待线程2也执行exchange()方法，当两个线程都到达同步点时，两个线程
 *  就可以交换数据
 * Created by Jack
 */
public class ExchangerTest {
    final Exchanger<String> exchanger=new Exchanger<String>();
    ExecutorService service= Executors.newFixedThreadPool(2);
    @Test
    public void exchange(){
        service.submit(new Runnable() {
            public void run() {
                try {
                    exchanger.exchange("1111");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        service.submit(new Runnable() {
            public void run() {
                try {
                    String second="2222";
                    String first=exchanger.exchange(second);

                    System.out.println("first is:"+first+" second is:"+second);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
