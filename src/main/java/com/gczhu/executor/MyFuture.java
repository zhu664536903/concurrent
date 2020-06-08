package com.gczhu.executor;

import java.util.concurrent.*;
import java.util.concurrent.Executors;

/**
 * Future接口用来保存和操作任务的执行状态
 * Callable接口用来作为带返回状态的任务
 * Future和Callable需要和线程池搭配使用
 */
public class MyFuture {
    //带返回值的任务
    static class Task implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            System.out.println("任务开始执行");
            return Integer.valueOf(10);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService p = Executors.newFixedThreadPool(2);
        Future<Integer> res = p.submit(new Task());
        res.cancel(false);  //取消任务执行
        TimeUnit.SECONDS.sleep(5);
        System.out.println(res.isCancelled());//任务是否取消
        System.out.println(res.isDone());//任务是否执行完成
        System.out.println(res.get());//获得任务执行返回值，如果任务取消执行会抛异常

    }
}
