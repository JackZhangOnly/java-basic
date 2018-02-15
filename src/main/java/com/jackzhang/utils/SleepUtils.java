package com.jackzhang.utils;

import java.util.concurrent.TimeUnit;


/**
 * Created by Jack on 2018/2/15.
 */
public class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }
}
