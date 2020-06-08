package com.gczhu.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 循环屏障：累加计数器，当所有线程在这个计数器上wait，计数器加1，到达指定阈值唤所有等待线程
 * 应用场景，阻塞一批线程，数量到达后一起执行
 * 与CountDownLantch区别：
 *      CountDownLantch 是一批线程依赖另一批线程的执行结果
 *      CyclicBarrier 是凑齐一堆线程一起扫行
 * 使用注意：
 *      当有一个线程被打断或者限时等待时，会直接被唤醒开发执行，其他等待中的或者后来加入等待的线程也产即执行
 */
public class MyCyclicBarrier {
    static CyclicBarrier barrier =new CyclicBarrier(3);
    static class T extends Thread{
        public T(){

        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"到位");
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName()+"屏障被破坏,当前线程中断标志位："+Thread.currentThread().isInterrupted());
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"开始干活");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T t1 = new T();
        T t2 = new T();
        T t3 = new T();
        t1.start();
        t2.start();
        TimeUnit.SECONDS.sleep(2);
        t1.interrupt();
        t3.start();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("屏障是否被破坏："+barrier.isBroken());
        barrier.reset();
        System.out.println("重置屏障后的标志位："+barrier.isBroken());
    }
}
