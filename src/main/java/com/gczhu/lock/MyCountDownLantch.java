package com.gczhu.lock;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 计数锁：维护一个计数器，可将一个或者多个线程锁起来。
 * 使用场景：当一个问题需要拆分成多个子问题时使用，例如，多个线程分别处理不同的sheet，需要知道程序处理excel总耗时
 */
public class MyCountDownLantch {
    static CountDownLatch c=new CountDownLatch(3);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程"+Thread.currentThread().getName()+"开始执行");
                try {
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("线程"+Thread.currentThread().getName()+"执行完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("线程"+Thread.currentThread().getName()+"开始减1，当前计数值："+c.getCount());
                    c.countDown();
                    System.out.println("线程"+Thread.currentThread().getName()+"减1完成，当前计数值："+c.getCount());
                }

            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程"+Thread.currentThread().getName()+"开始执行");
                try {
                    TimeUnit.SECONDS.sleep(7);
                    System.out.println("线程"+Thread.currentThread().getName()+"执行完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("线程"+Thread.currentThread().getName()+"开始减1，当前计数值："+c.getCount());
                    c.countDown();
                    System.out.println("线程"+Thread.currentThread().getName()+"减1完成，当前计数值："+c.getCount());
                }

            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程"+Thread.currentThread().getName()+"开始执行");
                try {
                    TimeUnit.SECONDS.sleep(4);
                    System.out.println("线程"+Thread.currentThread().getName()+"执行完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("线程"+Thread.currentThread().getName()+"开始减1，当前计数值："+c.getCount());
                    c.countDown();
                    System.out.println("线程"+Thread.currentThread().getName()+"减1完成，当前计数值："+c.getCount());
                }

            }
        });
        long startTime = System.currentTimeMillis();
        t1.start();
        t2.start();
        t3.start();

        c.await();
        long endTime = System.currentTimeMillis();
        System.out.println("excel处理完成，总耗时："+(endTime-startTime)/1000);

    }
}
