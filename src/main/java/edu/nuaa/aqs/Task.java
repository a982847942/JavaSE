package edu.nuaa.aqs;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/27 14:04
 */
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Task implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();
    public static int count = 0;

    // ReentrantLock的简单使用案例
    @SneakyThrows
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            lock.lock(); // 第一次阻塞式获取锁
            lock.tryLock(); // 第二次非阻塞式获取锁
            lock.tryLock(10,TimeUnit.SECONDS); // 第三次非阻塞等待式获取锁
            try {
                count++; // 非原子性操作：存在线程安全问题
            } finally {
                lock.unlock(); // 第一次释放锁
                lock.unlock(); // 第二次释放锁
                lock.unlock(); // 第三次释放锁
            }
        }
    }

    public void reentrantLockApiTest() {
        lock.lock(); // 获取锁
        try {
            //获取当前线程调用lock()方法的次数
            System.out.println("线程：" + Thread.currentThread().getName() + "\t调用lock()次数：" + lock.getHoldCount());
            // 判断当前锁是否为公平锁
            System.out.println("当前锁资源类型是否为公平锁？" + lock.isFair());
            // 获取等待获取当前锁资源的估计线程数
            System.out.println("目前有：" + lock.getQueueLength() + "个线程正在等待获取锁资源！");
            // 指定线程是否在等待获取当前锁资源
            System.out.println("当前线程是否在等待获取当前锁资源？" + lock.hasQueuedThread(Thread.currentThread()));
            // 判断当前锁资源是否有线程在等待获取
            System.out.println("当前锁资源是否存在线程等待获取？" + lock.hasQueuedThreads());
            // 判断当前线程是否持有当前锁资源
            System.out.println("当前线程是否持有当前锁资源？" + lock.isHeldByCurrentThread());
            // 判断当前锁资源是否被线程（不一定是当前线程）持有
            System.out.println("当前锁资源是否被线程占用？" + lock.isLocked());
        } finally {
            lock.unlock(); // 释放锁
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Task task = new Task();
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(count); // 执行结果：20000
        /**
         * 执行结果：
         *   线程：main	调用lock()次数：1
         *   当前锁资源类型是否为公平锁？false
         *   目前有：0个线程正在等待获取锁资源！
         *   当前线程是否在等待获取当前锁资源？false
         *   当前锁资源是否存在线程等待获取？false
         *   当前线程是否持有当前锁资源？true
         *   当前锁资源是否被线程占用？true
         */
        task.reentrantLockApiTest();
    }
}

