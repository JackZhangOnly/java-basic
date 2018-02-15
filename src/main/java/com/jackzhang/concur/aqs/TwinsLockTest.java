/**
 * 
 */
package com.jackzhang.concur.aqs;
import com.jackzhang.utils.SleepUtils;

import java.util.concurrent.locks.Lock;
/**
 * Created by Jack on 2018/2/15.
 */
public class TwinsLockTest {

    public void test() {
        final Lock lock = new TwinsLock();
        class Worker extends Thread {
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        SleepUtils.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepUtils.second(1);
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
        //启动10个线程
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.setName("线程-----"+i);
            w.start();
        }
        for (int i = 0; i < 10; i++) {
            SleepUtils.second(1);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        new TwinsLockTest().test();
    }
}
