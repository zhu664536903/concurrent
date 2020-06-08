package com.gczhu.executor;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 线程池与计数栓配合，并行处理多任务
 */
public class Executors {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(15);
        int poolSize = Integer.max(Runtime.getRuntime().availableProcessors(),5);

        System.out.println(poolSize);

        ExecutorService executorService = java.util.concurrent.Executors.newFixedThreadPool(poolSize);
        for (int i=1;i<=15;i++){
            executorService.execute(()->{
                System.out.println(Thread.currentThread().getName()+"正在运行");
                try {
                    TimeUnit.SECONDS.sleep(new Random().nextInt(8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                    System.out.println(Thread.currentThread().getName()+"运行完成，"+"当前计数器数量："+countDownLatch.getCount());
                }
            });
        }
        countDownLatch.await();
        System.out.println("运行结束。。。");
        executorService.shutdown();
    }
}
