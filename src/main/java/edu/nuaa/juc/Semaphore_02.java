package edu.nuaa.juc;

import java.util.concurrent.TimeUnit;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/30 21:01
 */
public class Semaphore_02 {
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        new Thread(()->{
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("exit");
        }).start();
        new Thread(()->{
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("exit");
        }).start();
        TimeUnit.MILLISECONDS.sleep(1000);
        /**
         * 我只release一次，结果会唤醒两个线程，假唤醒就出现了,解决方案见setHeadAndPropagate的todo，但是也不对
         */
        // TODO: 2024/1/30
        semaphore.release();
    }
}
