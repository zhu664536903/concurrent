package com.gczhu.automic;

import java.util.concurrent.*;

/**
 * compare and sweap 比较和确认操作
 * cas核心思想：
 * 具有内存位置，期望原值，新值三个操作数，当要操作内存位置值时，如果内存位置值与期望原值一致时，将新值写入内存位置并返回成功标志位，
 * 否则返回失败标志位。
 *
 * 实现方式：
 *  1.缩小锁的使用范围
 *  2.数据操作校验
 *  3.内存可见性
 *
 *  cas缺点：
 *  1.当并发量比较大时，CAS操作可能成功的概率低
 *  2.当内存位置值由A-B-A顺序变化时，使用CAS无法校验
 */
public class Cas {
     volatile static int count = 0;

    //模拟用户请求操作
    public static   void request() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(5);
        int expectValue ;
        do {
            expectValue = getCount();

        }while (!compareAndSweap(expectValue,expectValue+1));

    }

    //CAS
     static synchronized boolean compareAndSweap(int expectValue, int newValue) {
        if (expectValue == getCount()) {
            count = newValue;
            return true;
        }else{
            System.out.println("线程："+Thread.currentThread().getName()+"自旋!");
            return false;
        }
    }

    static int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        int threadSize = 10;
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

        System.out.println("消耗时间："+(end-start)+",线程数："+threadSize+",累加值："+count);

    }
}
