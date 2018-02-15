package com.jackzhang.concur.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by Jack on 2018/2/15.
 */
public class TwinsLock implements Lock{

    private final Sync sync=new Sync(2);
    private static final class Sync extends AbstractQueuedSynchronizer{
        Sync(int count){
            if (count<0){
                throw new IllegalArgumentException("count must large than zero");
            }
            //setState(count);
            compareAndSetState(0,count);
        }
        public int tryAcquireShared(int reduceCount) {
            for (;;){
                int curent=getState();
                int newCount=curent-reduceCount;
                if(newCount<0||compareAndSetState(curent,newCount)){
                    return newCount;
                }
            }
        }
        public int curState(){
            int count=getState();
            return count;
        }
        @Override
        public boolean tryReleaseShared(int returnCount) {
            for (;;){
                int current=getState();
                int newCount=current+returnCount;
                if (compareAndSetState(current,newCount)){
                    return true;
                }
            }
        }
        final ConditionObject newCondition() {
            return new ConditionObject();
        }
    }

    public void lock() {
        sync.acquireShared(1);
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    public boolean tryLock() {
        return sync.tryAcquireShared(1)>=0;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1,unit.toNanos(time));
    }

    public void unlock() {
        sync.releaseShared(1);

       System.out.println("getState========="+sync.curState());
    }

    public Condition newCondition() {
        return sync.newCondition();
    }
}
