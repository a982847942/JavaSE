package edu.nuaa.aqs;

import java.util.concurrent.Semaphore;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/28 19:38
 */
public class SemaphoreDemo_02 {
    // 这里将信号量设置成了0
    private static Semaphore sem = new Semaphore(0);
    private static class Thread1 extends Thread {
        @Override
        public void run() {
            // 获取锁
            sem.acquireUninterruptibly();
            System.out.println("获取锁成功");
        }
    }
    private static class Thread2 extends Thread {
        @Override
        public void run() {
            // 释放锁
            sem.release();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10000000; i++) {
            Thread t1 = new Thread1();
            Thread t2 = new Thread1();
            Thread t3 = new Thread2();
            Thread t4 = new Thread2();
            t1.start();
            t2.start();
            //head(signal) -> t1(signal) -> t2(0)
            t3.start();
            t4.start();
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            System.out.println(i);
        }
    }
}
