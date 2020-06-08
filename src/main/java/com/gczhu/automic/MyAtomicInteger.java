package com.gczhu.automic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AutomicInteger底层采用的是CAS操作
 */
public class MyAtomicInteger {
     static AtomicInteger count = new AtomicInteger();

    //模拟用户请求操作
    public static   void request() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(5);
        count.incrementAndGet();
    }
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        int threadSize = 100;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
        for (int i = 1; i <=threadSize ; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int j = 0; j < 10; j++) {
                                request();
                        }
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();

                    }finally {
                        countDownLatch.countDown();
                    }

                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("消耗时间："+(end-start)+",线程数："+threadSize+",累加值："+count.get());

    }
}
