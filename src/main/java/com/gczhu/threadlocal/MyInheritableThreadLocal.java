package com.gczhu.threadlocal;

import java.util.concurrent.TimeUnit;

/**
 * 读法：in'he ri ter ble
 * 可继承的ThreadLocal，当父线程创建多个子线程时，子线程自动继承父线程的变量
 */
public class MyInheritableThreadLocal {
    static InheritableThreadLocal inheritableThreadLocal = new InheritableThreadLocal();

    public static void main(String[] args) throws InterruptedException {
        inheritableThreadLocal.set("I'am value");
        Thread thread = new Thread(){
            @Override
            public void run() {
                System.out.println(inheritableThreadLocal.get());
                System.out.println("我是子线程"+Thread.currentThread().getName());
            }
        };
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        inheritableThreadLocal.remove();

    }
}
