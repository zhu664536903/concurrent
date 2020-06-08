package com.gczhu.automic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 原子标记引用类用来解决cas操作中不能识别数据A-B-A变化的问题
 * 实现原理：在内部将obj和stamp封装成一个对象，当cas操作中发现obj或者stamp变化，重新
 */
public class MyAtomicStampedReference {
    static class User{
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    public static void main(String[] args) {
        User user1 = new User();
        User user2 = new User();
        AtomicStampedReference<User> stamp = new AtomicStampedReference<>(user1, 0);
        User expectUser = user1;
        int expectStamp = stamp.getStamp();
        /**
         * 通过两次交换模拟  A-B-A问题
         */
        //第一次交换
        boolean one = stamp.weakCompareAndSet(user1, user2, stamp.getStamp(), stamp.getStamp() + 1);
        System.out.println("第一次交换结果："+one);

        //第二次交换
        boolean two = stamp.weakCompareAndSet(user2, user1, stamp.getStamp(), stamp.getStamp() + 1);
        System.out.println("第二次交换结果："+two);


        /**
         * 当前user对象虽然与期望user对象相等，但当前标记stamp与期望标记不一致，cas操作会失败
         */
        boolean three = stamp.weakCompareAndSet(expectUser, user2, expectStamp, expectStamp+1);
        System.out.println("第三次交换结果："+three);
        
    }
}
