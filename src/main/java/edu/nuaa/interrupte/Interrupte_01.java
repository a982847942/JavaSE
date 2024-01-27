package edu.nuaa.interrupte;

import java.util.concurrent.TimeUnit;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/26 18:55
 * //中断线程（实例方法）
 * public void Thread.interrupt();
 * //判断线程是否被中断（实例方法）
 * public boolean Thread.isInterrupted();
 * //判断是否被中断并清除当前中断状态（静态方法）
 * public static boolean Thread.interrupted();
 */
public class Interrupte_01 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                //while在try中，通过异常中断就可以退出run循环
                try {
                    while (true) {
                        //当前线程处于阻塞状态，异常必须捕捉处理，无法往外抛出
                        TimeUnit.SECONDS.sleep(2);
                    }
                } catch (InterruptedException e) {
                    System.out.println("Interruted When Sleep");
                    boolean interrupt = this.isInterrupted();
                    //中断状态被复位
                    System.out.println("interrupt:"+interrupt);
                }
            }
        };
        t1.start();
        TimeUnit.SECONDS.sleep(2);
        //中断处于阻塞状态的线程
        t1.interrupt();

        /**
         * 输出结果:
         Interruted When Sleep
         interrupt:false
         */
    }

}
