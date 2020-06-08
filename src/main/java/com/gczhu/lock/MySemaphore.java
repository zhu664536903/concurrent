package com.gczhu.lock;

import java.util.Timer;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 信号量：维护一定数量的信号，当信号数量不满足线程所需要的数量时线程阻塞，
 * 应用场景：限流
 * 使用注意点：确保获得信号，执行完再释放信号
 */
public class MySemaphore {
    private static Semaphore semaphore = new Semaphore(2);
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"争夺锁"+"当前锁数量："+semaphore.availablePermits());
                try {
                    semaphore.acquire(2);
                    System.out.println(Thread.currentThread().getName()+"获得锁"+"当前锁数量："+semaphore.availablePermits());
                    TimeUnit.SECONDS.sleep(5);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    //semaphore.release(2);
                    System.out.println(Thread.currentThread().getName()+"释放锁"+"当前锁数量："+semaphore.availablePermits());
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"争夺锁"+"当前锁数量："+semaphore.availablePermits());
                try {
                    semaphore.acquire(2);
                    System.out.println(Thread.currentThread().getName()+"获得锁"+"当前锁数量："+semaphore.availablePermits());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release(2);
                    System.out.println(Thread.currentThread().getName()+"释放锁"+"当前锁数量："+semaphore.availablePermits());
                }
            }
        });
        t1.start();
        TimeUnit.SECONDS.sleep(2);
        t2.start();
        //t2.interrupt();
    }
}
