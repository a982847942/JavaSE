package edu.nuaa.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author brain
 * @version 1.0
 * @date 2023/10/31 11:36
 */
public class CompletableFuture_02 {
    public static void main(String[] args) throws Exception {
        // 创建有返回值的异步任务
        CompletableFuture<String> supplyCF = CompletableFuture
                .supplyAsync(CompletableFuture_02::evenNumbersSum);
        // 执行成功的回调
        supplyCF.thenAccept(System.out::println);
        // 执行过程中出现异常的回调
        supplyCF.exceptionally((e) -> {
            e.printStackTrace();
            return "异步任务执行过程中出现异常....";
        });
        // 主线程执行打印1234...操作
        // 因为如果不为CompletableFuture指定线程池执行任务的情况下，
        // CompletableFuture默认是使用ForkJoinPool.commonPool()的线程
        // 同时是作为main线程的守护线程进行的，如果main挂了，执行异步任
        // 务的线程也会随之终止结束，并不会继续执行异步任务
        for (int i = 1; i <= 10; i++) {
            System.out.println("main线程 - 输出：" + i);
            Thread.sleep(50);
        }

        /***************************************************/

        // 创建一个异步任务，已经给定返回值了
        CompletableFuture c = CompletableFuture.completedFuture("竹子");
        c.thenApply(r -> {
            System.out.println("上个任务结果：" + r);
            return r + "...熊猫";
        });
        c.thenAccept(System.out::println);

        /***************************************************/

        // 创建一个没有返回值的异步任务
        CompletableFuture runCF = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "没有返回值的异步任务");
        });

        /***************************************************/

        // 创建单例的线程池
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // 创建一个有返回值的异步任务并指定执行的线程池
        CompletableFuture<String> supplyCFThreadPool =
                CompletableFuture.supplyAsync(CompletableFuture_02::oddNumbersSum, executor);
        // // 执行过程中出现异常的回调
        supplyCFThreadPool.thenAccept(System.out::println);
        // 执行过程中出现异常的回调
        supplyCF.exceptionally((e) -> {
            e.printStackTrace();
            return "异步任务执行过程中出现异常....";
        });

        // 关闭线程池
        executor.shutdown();
    }

    // 求和100内的偶数
    private static String evenNumbersSum() {
        int sum = 0;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0) sum += i;
        }
        return Thread.currentThread().getName() + "线程 - 100内偶数之和：" + sum;
    }

    // 求和100内的奇数
    private static String oddNumbersSum() {
        int sum = 0;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= 100; i++) {
            if (i % 2 != 0) sum += i;
        }
        return Thread.currentThread().getName() + "线程 - 100内奇数之和：" + sum;
    }

}
