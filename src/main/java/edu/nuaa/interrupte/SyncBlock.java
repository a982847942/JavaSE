package edu.nuaa.interrupte;

import java.util.concurrent.TimeUnit;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/26 19:05
 */

public class SyncBlock implements Runnable {
    /**
     * interrupt对于synchronized阻塞的线程无效
     */
    public synchronized void occupyLock() {
        System.out.println("Trying to call occupyLock()");
        while (true) // 从不释放锁
            Thread.yield();
    }

    /**
     * 在构造器中创建新线程并启动获取对象锁
     */
    public SyncBlock() {
        //该线程已持有当前实例锁
        new Thread() {
            public void run() {
                occupyLock(); // 当前线程获取锁
            }
        }.start();
    }

    public void run() {
        //中断判断
        while (true) {
            if (Thread.interrupted()) {
                System.out.println("中断线程!!");
                break;
            } else {
                occupyLock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SyncBlock sync = new SyncBlock();
        Thread t = new Thread(sync);
        //启动后调用occupyLock()方法,无法获取当前实例锁处于等待状态
        t.start();
        TimeUnit.SECONDS.sleep(1);
        //中断线程,无法生效
        t.interrupt();
    }
}


