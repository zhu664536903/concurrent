package com.gczhu.automic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Unsafe类提供运行级别低，非安全的底层操作，慎用
 * 1.可直接操作内存
 * 2.线程调度
 * 3.cas
 * 4.class相关
 * 5.对象操作
 */
public class MyUnsafe {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        System.out.println(field.get(null));
    }
}
