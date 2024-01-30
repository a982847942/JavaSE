package edu.nuaa.juc;

import java.util.concurrent.CompletableFuture;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/29 15:32
 */
public class CompletionStage {
    /**
     * CompletionStage接口中描述任务之间汇聚关系的方法总供有两类，一类是and类型的，代表着任务都要执行完成后才开始处理的方法。
     * 另一类则是or类型的，代表着任务只要有任意一个执行完成就开始处理的方法。提供的方法如下：
     * <p>
     * AND类型：
     * thenCombine系列：可以接收前面任务的结果进行汇聚计算，并且计算后可以返回值
     * thenAcceptBoth系列：可以接收前面任务的结果进行汇聚计算，但计算后没有返回值
     * runAfterBoth系列：不可以接收前面任务的结果且无返回，但可以在任务结束后进行汇聚计算
     * CompletableFuture类的allOf系列：不可接收之前任务的结果，但可以汇聚多个任务，但是要配合回调处理方法一起使用
     * <p>
     * <p>
     * OR类型：
     * applyToEither系列：接收最先完成的任务结果进行处理，处理完成后可以返回值
     * acceptEither系列：接收最先完成的任务结果进行处理，但是处理完成后不能返回
     * runAfterEither系列：不能接收前面任务的返回值且无返回，单可以为最先完成的任务进行后继处理
     * CompletableFuture类的anyOf系列：可以同时汇聚任意个任务，并接收最先执行完成的任务结果进行处理，处理完成后没有返回值，需要配合回调方法一起使用
     */

    public static void main(String[] args) throws Exception {
        /*--------------------创建两个异步任务CF1/CF2------------------*/
        CompletableFuture<Integer> cf1 =
                CompletableFuture.supplyAsync(CompletionStage::evenNumbersSum);
        CompletableFuture<Integer> cf2 =
                CompletableFuture.supplyAsync(CompletionStage::oddNumbersSum);

        /*--------------------测试AND类型汇聚方法------------------*/
        CompletableFuture<Integer> cfThenCombine = cf1.thenCombine(cf2, (r1, r2) -> {
            System.out.println("cf1任务计算结果：" + r1);
            System.out.println("cf2任务计算结果：" + r2);
            return r1 + r2;
        });
        System.out.println("cf1,cf2任务ThenCombine汇聚处理结果：" + cfThenCombine.get());

        // thenAcceptBoth、runAfterBoth系列与thenCombine差不多相同
        // 区别就在于入参BiFunction、BiConsumer、Runnable三个函数式接口的不同

        // 使用allOf汇聚两个任务（可以汇聚多个）
        CompletableFuture cfAllOf = CompletableFuture.allOf(cf1, cf2);
        // 配合thenAccept成功回调函数使用
        cfAllOf.thenAccept(o -> System.out.println("所有任务完成后进行断后处理...."));

        //分割线
        Thread.sleep(2000);
        System.err.println("--------------------------------------");

        /*--------------------测试OR类型汇聚方法------------------*/
        CompletableFuture<Integer> cfApplyToEither = cf1.applyToEither(cf2, r -> {
            System.out.println("最先执行完成的任务结果：" + r);
            return r * 10;
        });
        System.out.println("cf1,cf2任务applyToEither汇聚处理结果：" + cfApplyToEither.get());


        // acceptEither、runAfterEither系列与applyToEither系列也差不多相同
        // 区别就也是在于入参BiFunction、BiConsumer、Runnable三个函数式接口的不同

        // 使用anyOf汇聚两个任务，谁先执行完成就处理谁的执行结果
        CompletableFuture cfAnyOf = CompletableFuture.anyOf(cf1, cf2);
        // 配合thenAccept成功回调函数使用
        cfAnyOf.thenAccept(r -> {
            System.out.println("最先执行完成的任务结果：" + r);
            System.out.println("对先完成的任务结果进行后续处理....");
        });
    }

    // 求和100内的偶数
    private static int evenNumbersSum() {
        int sum = 0;
        try {
            Thread.sleep(800); // 模拟耗时
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
            Thread.sleep(1000); // 模拟耗时
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= 100; i++) {
            if (i % 2 != 0) sum += i;
        }
        return sum;
    }

}
