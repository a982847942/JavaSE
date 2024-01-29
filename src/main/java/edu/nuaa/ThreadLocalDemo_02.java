package edu.nuaa;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/29 9:10
 */
public class ThreadLocalDemo_02 {
    private static InheritableThreadLocal<String> itl =
            new InheritableThreadLocal<String>();
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName()
                + "......线程执行......");
        itl.set("竹子....");
        System.out.println("父线程：main线程赋值：竹子....");
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()
                    + "......线程执行......");
            System.out.println("子线程：T1线程读值："+itl.get());
        },"T1").start();
        System.out.println("执行结束.....");
    }

}
