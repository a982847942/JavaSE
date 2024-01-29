package edu.nuaa.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/28 20:13
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        int num = 7;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(num, () -> {
            System.out.println("*****开始召唤神龙******");
        });

        for (int i = 1; i <= num; i++) {
            final int temp = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t收集到第" + temp + "龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i) ).start();
        }
    }
}

