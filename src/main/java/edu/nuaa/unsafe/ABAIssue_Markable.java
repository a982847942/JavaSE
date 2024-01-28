package edu.nuaa.unsafe;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/27 10:55
 */
public class ABAIssue_Markable {
    /**
     * 不能完全解决ABA问题
     */
    static AtomicMarkableReference<Integer> amRef = new AtomicMarkableReference<Integer>(100, false);

    private static Thread t1 = new Thread(() -> {
        boolean mark = amRef.isMarked();
        System.out.println("线程T1：修改前标志 Mrak:" + mark + "....");
        // 将值更新为200
        System.out.println("线程T1：100 --> 200.... 修改后返回值 Result:" + amRef.compareAndSet(amRef.getReference(), 200, mark, !mark));
    });

    private static Thread t2 = new Thread(() -> {
        boolean mark = amRef.isMarked();
        System.out.println("线程T2：修改前标志 Mrak:" + mark + "....");
        // 将值更新回100
        System.out.println("线程T2：200 --> 100.... 修改后返回值 Result:" + amRef.compareAndSet(amRef.getReference(), 100, mark, !mark));
    });

    private static Thread t3 = new Thread(() -> {
        boolean mark = amRef.isMarked();
        System.out.println("线程T3：休眠前标志 Mrak:" + mark + "....");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean flag = amRef.compareAndSet(100, 500, mark, !mark);
        System.out.println("线程T3： 100 --> 500.... flag:" + flag + ",newValue:" + amRef.getReference());
    });

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();

        /**
         * 输出结果如下:
         *    线程T1：修改前标志 Mrak:false....
         *    线程T1：100 --> 200.... 修改后返回值 Result:true
         *    线程T2：修改前标志 Mrak:true....
         *    线程T2：200 --> 100.... 修改后返回值 Result:true
         *    线程T3：休眠前标志 Mrak:false....
         *    线程T3： 100 --> 500.... flag:true,newValue:500
         */

         /* t3线程执行完成后结果还是成功更新为500，代表t1、t2
          线程所做的修改操作对于t3线程来说还是不可见的 */
    }

}
