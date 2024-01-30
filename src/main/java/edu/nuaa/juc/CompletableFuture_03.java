package edu.nuaa.juc;

import java.util.concurrent.CompletableFuture;

/**
 * @author brain
 * @version 1.0
 * @date 2023/10/31 11:55
 */
public class CompletableFuture_03 {
    public static void main(String[] args) throws Exception {
        /**
         * ①求和100内的所有偶数
         * ②基于第一个任务的结果再加上100内奇数总值计算100内所有数字的总和
         * ③基于第二个任务的结果除0抛出一个异常
         * ④使用handle创建一个可以在上个任务抛出异常时依旧执行的任务
         * ⑤使用thenCompose创建一个基于上个任务返回值+1的任务
         * ⑥使用thenRun创建了一个没有返回值的任务
         *
         * ①thenApply类：此类方法可以基于上一个任务再创建一个新的有返回型任务。
         * ②handle类：与thenApply类作用相同，不同点在于thenApply类方法只能在上一个任务执行正常的情况下才能执行，当上一个任务执行抛出异常后则不会执行。而handle类在上个任务出现异常的情况下也可以接着执行。
         * ③thenRun类：此类方法可以基于上一个任务再创建一个新的无返回型任务。
         * ④thenCompose类：与thenApply类大致相同，不同点在于每次向下传递都是新的CompletableFuture对象，而thenApply向下传递的都是同一个CompletableFuture对象对象
         */
        CompletableFuture cf =
                CompletableFuture.supplyAsync(CompletableFuture_03::evenNumbersSum)
                        // 链式编程：基于上个任务的返回继续执行新的任务
                        .thenApply(r -> {
                            System.out.println("获取上个任务的执行结果：" + r);
                            // 通过上个任务的执行结果完成计算：求和100所有数
                            return r + oddNumbersSum();
                        }).thenApplyAsync(r -> {
                            System.out.println("获取上个任务的执行结果：" + r);
                            Integer i = r / 0; // 拋出异常
                            return r;
                        }).handle((param, throwable) -> {
                            if (throwable == null) {
                                return param * 2;
                            }
                            // 获取捕获的异常
                            System.out.println(throwable.getMessage());
                            System.out.println("我可以在上个任务" +
                                    "抛出异常时依旧执行....");
                            return -1;
                        }).thenCompose(x ->
                                CompletableFuture.supplyAsync(() -> {
                                            System.out.println("thenCompose:" + x);
                                            return x + 1;
                                        }
                                )).thenRun(() -> {
                            System.out.println("我是串行无返回任务....");
                        });

        // 主线程执行休眠一段时间
        // 因为如果不为CompletableFuture指定线程池执行任务的情况下，
        // CompletableFuture默认是使用ForkJoinPool.commonPool()的线程
        // 同时是作为main线程的守护线程进行的，如果main挂了，执行异步任
        // 务的线程也会随之终止结束，并不会继续执行异步任务
        Thread.sleep(2000);
    }

    // 求和100内的偶数
    private static int evenNumbersSum() {
        int sum = 0;
        try {
            Thread.sleep(100);
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
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= 100; i++) {
            if (i % 2 != 0) sum += i;
        }
        return sum;
    }

}
