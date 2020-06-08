package com.gczhu.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MyLinkedBlockingQueue {
    //1.新建一个有界阻塞队列
    private LinkedBlockingDeque<String> abq = new LinkedBlockingDeque<String>();

    //2.put方法
    public void put(String msg) throws InterruptedException {
        if(!abq.offer(msg)){
            //Thread.currentThread().interrupt();
            System.out.println("队列已满，停止生产");
        }
    }
    //3.take方法
    public String get() throws InterruptedException {
        return abq.take();
    }

    public static void main(String[] args) {
        MyLinkedBlockingQueue myArrayBlockingQueue = new MyLinkedBlockingQueue();
        AtomicInteger atomicInteger = new AtomicInteger();
        new Thread(()->{
            try {while (true){
                myArrayBlockingQueue.put(Thread.currentThread().getName()+String.format("消息%s",atomicInteger.getAndIncrement()));
                TimeUnit.MILLISECONDS.sleep(10);
            }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                while (true){
                    myArrayBlockingQueue.put(Thread.currentThread().getName()+String.format("消息%s",atomicInteger.getAndIncrement()));
                    System.out.println(myArrayBlockingQueue.get());
                    TimeUnit.MILLISECONDS.sleep(500);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
