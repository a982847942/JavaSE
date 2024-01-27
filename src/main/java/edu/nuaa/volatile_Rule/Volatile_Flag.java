package edu.nuaa.volatile_Rule;

import java.util.concurrent.TimeUnit;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/26 10:25
 */
public class Volatile_Flag {
    /*volatile*/ boolean flag = true;
    int a = 10;
    private void run() {
        while (flag) {
//            a++;
//            System.out.println("正在运行");
            if (++a == 10_000){
                Thread.yield();
            }
        }
    }

    private void stop() {
        flag = false;
    }


    public static void main(String[] args) throws InterruptedException {
        Volatile_Flag volatileFlag = new Volatile_Flag();
        /**
         * 程序一直卡在这里的根本原因在于flag被JIT编译器优化后 flag一直使用的是工作内存中的数据，stop方法修改了自己的flag值刷新回主内存后，
         * run线程没有感知到。因此只要想办法让其能够感知flag值的变化，强制回主内存读取一遍即可。
         * 方法可以有，但不限于：
         * 1.指定-Xint纯解释执行，不适用JIT编译器
         * 2.使用volatile 使flag具有可见性
         * 3.在run中使用一些技巧，强制其访问主内存。比如：使用Integer类型的变量操作，标准输出操作,Thread.yield()主动让出一次CPU
         * 4.不要让TimeUnit.MILLISECONDS.sleep(10)这个操作，也就是说不要让run线程执行足够多次数使其被JIT优化，就立刻执行stop线程。
         */
        new Thread(() -> {
            volatileFlag.run();
        }).start();
        TimeUnit.MILLISECONDS.sleep(10);
        new Thread(() -> {
            volatileFlag.stop();
        }).start();
    }


}
