package edu.nuaa.juc;

import java.util.concurrent.CompletableFuture;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/29 15:27
 */
public class CompletableFuture_04 {
    /**
     * ①主线程调用直接获取执行结果的get、getNow方法
     * ②可以为无返回值的异步任务写出执行结果的：complete开头的方法
     * ②任务正常执行成功的回调：thenAccept开头的方法
     * ④任务执行抛出异常的回调：exceptionally方法
     * ⑤任务执行结束的回调：whenComplete开头的方法
     *
     * CompletableFuture类中的实现中，总归有五大类方法用来描述任务之间串行关系的方法，
     * 它们分别为：thenApply、thenAccept、thenRun、handle以及thenCompose，
     * 在CompletableFuture类中方法名以这五个开头的函数都是用来描述任务之间的串行关系。
     *
     *
     */
        public static void main(String[] args) throws Exception {
            CompletableFuture cf1 =
                    CompletableFuture.supplyAsync(CompletableFuture_04::evenNumbersSum);
            CompletableFuture cf2 =
                    CompletableFuture.supplyAsync(CompletableFuture_04::oddNumbersSum);

            // 防止main线程死亡导致执行异步任务的线程也终止执行
            Thread.sleep(3000);
        }

        // 求和100内的偶数
        private static int evenNumbersSum() {
            int sum = 0;
            System.out.println(Thread.currentThread().getName()
                    +"线程...执行了求和偶数....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 1; i <= 100; i++) {
                if (i % 2 == 0) sum += i;
            }
            return sum;
        }

        // 求和100内的奇数
        private static int oddNumbersSum() {
            int sum = 0;
            System.out.println(Thread.currentThread().getName()
                    +"线程...执行了求和奇数....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 1; i <= 100; i++) {
                if (i % 2 != 0) sum += i;
            }
            return sum;
        }
        /* *
         * 执行结果：
         *      ForkJoinPool.commonPool-worker-1线程...执行了求和偶数....
         *      ForkJoinPool.commonPool-worker-2线程...执行了求和奇数....
         * */

}
