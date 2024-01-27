package edu.nuaa.interrupte;

import java.util.concurrent.TimeUnit;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/26 18:57
 */
public class Interrupte_02 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(){
            @Override
            public void run(){
                while(true){
                    System.out.println("未被中断");
                    //判断当前线程是否被中断
                    if (this.isInterrupted()){
                        System.out.println("线程中断");
                        break;
                    }
                }
            }
        };
        t1.start();
        TimeUnit.SECONDS.sleep(2);
        //处于运行态的线程如果不主动处理中断，则该次调用无效
        t1.interrupt();

        /**
         * 输出结果(无限执行):
         未被中断
         未被中断
         未被中断
         ......
         */
    }

}
