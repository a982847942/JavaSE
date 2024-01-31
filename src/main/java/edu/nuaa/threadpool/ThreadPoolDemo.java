package edu.nuaa.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/30 9:36
 */
public class ThreadPoolDemo {

    public static void main(String[] args) {

        /*
         *  创建可缓存的线程池
         *  优点：当线程池中线程执行完任务后会将线程缓存起来，默认60s后空闲线程会自动回收
         *  缺点：任然存在由于并发过高导致瞬间创建大量线程产生的OOM
         */
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(() -> {
            System.out.println("我是递交到cachedThreadPool的异步任务....竹子....");
        });

        /*
         *  创建定长的线程池
         *  优点：可以避免由于并发过高导致瞬间创建大量线程产生的OOM
         *  缺点：
         *      1. 线程创建后永不释放线程资源
         *      2. 任务队列最大长度为Integer.MAX_VALUE，并发时会创建大量的任务导致OOM
         */
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        Future<?> futureResult = fixedThreadPool.submit(() -> {
            System.out.println("我是递交到fixedThreadPool的异步任务....竹子...");
            return "竹子";
        });
        try {
            // 得到执行后返回结果
            String str = (String) futureResult.get();
            System.out.println("我是递交到fixedThreadPool的异步任务执行完成后的返回结果：" + str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        /*
         *  创建定长可支持周期调度的线程池
         *  优点：可以支持按时调度执行任务
         *  缺点：
         *      1. 线程创建后永不释放线程资源
         *      2. 任务队列最大长度为Integer.MAX_VALUE，并发时会创建大量的任务导致OOM
         */
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.schedule(()->{
            System.out.println("我是递交到scheduledThreadPool十秒钟之后执行的异步任务....熊猫...");
        },10,TimeUnit.SECONDS);

        /*
         *  创建单线程的线程池
         *  优点：可以支持线程池任务的执行按照递交的顺序先进先出(FIFO)
         *  缺点：单线程效率比不上前面的三种线程池（前面的线程池都存在多线程并行执行任务）
         */
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        List<Callable<String>> callables = new ArrayList<>();
        callables.add(()->{
            System.out.println("我是递交到singleThreadExecutor的异步任务...熊猫1号...");
            return "熊猫一号";
        });
        callables.add(()->{
            System.out.println("我是递交到singleThreadExecutor的异步任务...熊猫2号...");
            return "熊猫二号";
        });
        callables.add(()->{
            System.out.println("我是递交到singleThreadExecutor的异步任务...熊猫3号...");
            return "熊猫三号";
        });
        try {
            // 接收批量执行后的结果
            List<Future<String>> futures = singleThreadExecutor.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cachedThreadPool.shutdown();
        fixedThreadPool.shutdown();
        scheduledThreadPool.shutdown();
        singleThreadExecutor.shutdown();

        /* 执行结果：
         *  我是递交到cachedThreadPool的异步任务....竹子....
         *
         *  我是递交到fixedThreadPool的异步任务....竹子...
         *  我是递交到fixedThreadPool的异步任务执行完成后的返回结果：竹子
         *
         *  我是递交到singleThreadExecutor的异步任务...熊猫1号...
         *  我是递交到singleThreadExecutor的异步任务...熊猫2号...
         *  我是递交到singleThreadExecutor的异步任务...熊猫3号...
         *
         *  我是递交到scheduledThreadPool十秒钟之后执行的异步任务....熊猫...
         */
    }
}
