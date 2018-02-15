package com.jackzhang.concur.semaphore;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore(信号量)用来控制同时访问特定资源的线程数量，通过协调各个线程以保证合理有效的使用这些资源
 * 主要应用在对有限公用资源的访问上，如数据库连接（想像一下洗手间坑位，一个道理）
 * Created by Jack
 */
public class SemaphoreTest {
    private final int THREAD_NUM=30;
    private ExecutorService service= Executors.newFixedThreadPool(THREAD_NUM);
    //充许10个线程同时访问
    private Semaphore semaphore=new Semaphore(10);

    @Test
    public void connectDb(){
        for (int i=0;i<THREAD_NUM;i++){
            service.submit(new Runnable() {
                public void run() {
                    try {
                        //获得许可，获取不到则用阻塞
                        semaphore.acquire();
                        Thread.currentThread().sleep(1000);
                        System.out.println("connect to db ,save data");
                        //释放许可
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        try {
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
