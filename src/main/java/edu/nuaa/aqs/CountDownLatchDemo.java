package edu.nuaa.aqs;

import java.util.concurrent.CountDownLatch;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/28 20:13
 * @Description 多等一  模拟并发
 */
public class CountDownLatchDemo {
    static final CountDownLatch countDownLatch = new CountDownLatch(1);
    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 3; i++) {
            new Thread(() -> {
                try {
                    System.out.println("线程：" + Thread.currentThread().getName()
                            + "....阻塞等待！");
                    countDownLatch.await();
                    // 可以在此处调用需要并发测试的方法或接口
                    System.out.println("线程：" + Thread.currentThread().getName()
                            + "....开始执行！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "T" + i).start();
        }
        Thread.sleep(1000);
        countDownLatch.countDown();
/*
   程序开始运行：
    线程：T2....阻塞等待！
    线程：T1....阻塞等待！
    线程：T3....阻塞等待！
   程序运行一秒后（三条线程几乎同时执行）：
    线程：T2....开始执行！
    线程：T1....开始执行！
    线程：T3....开始执行！
*/

    }
}
