package com.gczhu.threadlocal;

/**
 *  每个ThreadLocal对象作为当前线程的变量，多个threadLocal对应多个变量
 *  应用场景：
 *       1.控制层-业务层-数据访问层存在共享变量
 *       2.多线程操作数据库，每个线程保存一个会话
 *  原理：
 *      每个线程内部都维护了一个map，entry  key为threadLocal对象，value为变量值
 *
 *  问题点：
 *      1.每次往map存入key value时，如果遇于哈希冲突，按数据下标+1方式线性添加，当哈希冲突较多时，性能较差
 *      2.map中entry对象的key为弱引用类型，下一次gc会回收key(threadLocal),但不回收value,当请求次数过多时会存在大量的不可回收对象
 *      最后导致OOM
 *  解决办法：
 *      1.每个线程只创建一个变量，避免哈希冲突
 *      2.变量使用后应调用threadLocal的remove方法移除变量对应entry对象和变量值
 */
public class MyThreadLocal {
        ThreadLocal<String> t = new ThreadLocal<>();
    public void controler(String ii){
        try{
            t.set(ii);
            System.out.println("控制层:"+t.get());
            service();
        }finally {
            t.remove();
        }



    }
    public void service(){
        System.out.println("业务层:"+t.get());
        dao();
    }
    public void dao(){
        System.out.println("数据访问层:"+t.get());
    }
    public static void main(String[] args) {
        MyThreadLocal myThreadLocal = new MyThreadLocal();
        myThreadLocal.controler("123");

    }
}
