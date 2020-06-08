package com.gczhu.automic;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 原子引用类型字段更新器是一个抽象类，主要用于对引用类型的字段进行原子操作,被操作字段必须使用volatile修饰
 *
 * 本次实现需求：多个线程对一个对象的一个属性操作，只有一个操作是成功的
 */
public class MyAtomicReferenceFieldUpdater {
    volatile Boolean isInit  = Boolean.FALSE;
    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater =
            AtomicReferenceFieldUpdater.newUpdater(MyAtomicReferenceFieldUpdater.class,Boolean.class,"isInit");
    public boolean init(){
        if (atomicReferenceFieldUpdater.compareAndSet(this,Boolean.FALSE,Boolean.TRUE)){
            System.out.println("线程："+Thread.currentThread().getName()+"完成对象的初始化");
            return true;
        }else{
            System.out.println("线程："+Thread.currentThread().getName()+"初始化失败，原因：已初始化");
            return false;
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        MyAtomicReferenceFieldUpdater myAtomicReferenceFieldUpdater = new MyAtomicReferenceFieldUpdater();
        for (int i = 0; i <10 ; i++) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                myAtomicReferenceFieldUpdater.init();
            }
        });

        }
        System.out.println( myAtomicReferenceFieldUpdater.isInit);
        executorService.shutdown();
    }

}
