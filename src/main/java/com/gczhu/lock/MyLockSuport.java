package com.gczhu.lock;

import java.util.Timer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程等待与通知第三种方式，区别在于它不需要获得锁，待等与通知方法没有顺序要求
 */
public class MyLockSuport {
    private static ReentrantLock lock = new ReentrantLock ();
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程运行中。。。。");
                    System.out.println("线程中断标志位：" + Thread.currentThread().isInterrupted());
                    LockSupport.park();
                    System.out.println("线程中断标志位：" + Thread.currentThread().isInterrupted());
                    System.out.println("线程被唤醒");

            }
        });

        t1.start();
        Thread.sleep(1000);
        LockSupport.unpark(t1);
        System.out.println("调用unpark方法");
    }

}
