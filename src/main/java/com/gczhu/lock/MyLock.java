package com.gczhu.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * lock 为语言级别锁，能够感知获得锁的过程，需要手动释放锁，可实现公平锁
 */
public class MyLock {
    private static int num=0;
    private static ReentrantLock lock=new ReentrantLock(false);
    public static void main(String[] args) {
        T t1 = new T();
        T t2 = new T();
        T t3 = new T();
        t1.start();
        t2.start();
        t3.start();
    }
    public static class  T extends Thread{
        @Override
        public void run() {
            for(int i=0;i<=100;i++){
                lock.lock();
                try {
                    Thread.sleep(500);

                    num++;
                    System.out.println("当前线程："+this.getName()+"num:"+num);
                }catch (Exception e){
                    System.out.println(e);
                }finally {
                    lock.unlock();
                }

            }
        }
    }

}
