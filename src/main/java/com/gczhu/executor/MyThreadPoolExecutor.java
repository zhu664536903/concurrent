package com.gczhu.executor;

import java.util.concurrent.*;

/**
 * 利用线程池来执行任务，手动创建线程池执行器可以避免调用静态方法出现的错误
 * 参数：核心线程数，最大线程数,线程空闲时间，时间单位,任务队列,停止策略
 * 静态方法容易出现的错误：
 *      1.固定数量线程池：底层采用linkedBlockingQueue允许无限添加任务，当任务过多容易造成OOM
 *      2.缓存线程池：底层采用syncronusBlockingQueue，不保存任务，不限制线程创建数量，当任务过多会导致
 *      创建过多线程，容易OOM
 *
 */
public class MyThreadPoolExecutor {
    static class Task implements Runnable{
        String tasksName;
        public Task(String name) {
            this.tasksName = name;
        }

        @Override
        public void run() {
            System.out.println("任务："+this.tasksName+"开始运行。");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return this.tasksName;
        }
    }
    public static void main(String[] args) {
        ThreadPoolExecutor tp = new ThreadPoolExecutor(1, 1, 60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r,"自定义线程");
                    }
                },
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println("任务：" + r.toString() + "无法处理");
                    }
                }){//重写线程池方法
                    @Override
                    protected void beforeExecute(Thread t, Runnable r) {
                        System.out.println("线程"+t.getName()+"开始执行任务:"+r.toString());
                    }
                };
        for (int i = 0; i < 5; i++) {
            tp.execute(new Task("任务"+i));
        }

    }
}
