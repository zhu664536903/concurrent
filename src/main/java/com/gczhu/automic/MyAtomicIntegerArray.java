package com.gczhu.automic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class MyAtomicIntegerArray {
    static AtomicIntegerArray atomicIntegerArray  = new AtomicIntegerArray(10);

    //模拟用户请求操作
    public static   void request(int i) throws InterruptedException {
        for (int j = 0; j < 10; j++) {
            TimeUnit.MILLISECONDS.sleep(5);
            atomicIntegerArray.getAndIncrement(i);
        }

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
                            request(j);
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

        System.out.println("消耗时间："+(end-start)+",线程数："+threadSize+",累加值："+atomicIntegerArray.toString());

    }
}
