package com.gczhu.completionservice;

import java.util.concurrent.*;

/**
 * CompletionService接口主要用于将任务提交至线程池，并按任务执行完成顺序将任务结果（Future）存入阻塞队列
 * 解决问题：自动保存先完成的任务执行结果，以便于按需求消费任务执行结果
 * 这个类实现需求：
 *      使用CompletionService实现类提交任务，并消费任务执行结果
 */
public class MyExecutorCompletionService {
    //带返回值任务
    static class task implements Callable<String>{
        private String taskName;
        int seconds;
        public task(String taskName,int seconds){
            this.taskName=taskName;
            this.seconds=seconds;
        }
        @Override
        public String call() throws Exception {
            long start = System.currentTimeMillis();
            System.out.println("任务："+this.taskName+"开始执行！");
            TimeUnit.SECONDS.sleep(seconds);
            long end = System.currentTimeMillis();
            return "任务："+this.taskName+"执行完成！总耗时："+(end-start)/1000+"s";
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //1.新建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //2.新建CompletionService对象
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(executorService);
        //3.提交任务
        int i=1;
        for (; i <=6; i++) {
            ecs.submit(new task(""+i,i));
        }
        //4.关闭线程池
        executorService.shutdown();
        //5.消费执行结果
        for (--i;i>0;i--){
            System.out.println(ecs.take().get());
        }

    }

}
