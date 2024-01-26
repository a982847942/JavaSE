package edu.nuaa.juc;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * @author brain
 * @Description 在Java中，创建线程的方式就只有一种：调用Thread.start()方法! 其它的都不是创建线程(包括继承Thread类重写run方法)，只是创建线程体
 * @version 1.0
 * @date 2024/1/25 19:37
 */
public class Create_Thread_Method {

    private void objectInfo() {
        System.out.println(GraphLayout.parseInstance(new Create_Thread_Method()).toPrintable());
        System.out.println(GraphLayout.parseInstance(new Create_Thread_Method()).totalSize());
        System.out.println(ClassLayout.parseInstance(new Create_Thread_Method()).toPrintable());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1.
//        new ExtendsThread().start();
        //2.
        new Thread(new ImplementsRunnable()).start();
        //3.
//        FutureTask<String> futureTask = new FutureTask<>(new ImplementsCallable());
//        new Thread(futureTask).start();
//        System.out.println(futureTask.get());
        //4.
//        useExecutors();
        //5.
//        useCompletableFuture();
        //6.
//        useThreadGroup();
        //7.直接使用FutureTask + 匿名类
//        FutureTask<String> futureTask = new FutureTask<>(() -> {
//            return "第七种方式";
//        });
//        new Thread(futureTask).start();
//        System.out.println(futureTask.get());
        //8.
//        useTimer();
        //9.
        useForkJoinPool();
    }

    public static void useForkJoinPool(){
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.execute(()->{
            System.out.println("9A......");
        });

        List<String> list = Arrays.asList("9B......");
        list.parallelStream().forEach(System.out::println);
    }
    public static void useTimer() {
        //挂在Timer下的任务都是一个线程执行
        Timer timer = new Timer();

        /**
         * 里面需要传入两个数字，第一个代表启动后多久开始执行，第二个代表每间隔多久执行一次，单位是ms毫秒。
         */
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("8......");
            }
        }, 0, 1000);
    }

    public static void useExecutors() {
        ExecutorService poolA = Executors.newFixedThreadPool(2);
        poolA.execute(() -> {
            System.out.println("4A......");
        });
        poolA.shutdown();

        // 又或者自定义线程池
        ThreadPoolExecutor poolB = new ThreadPoolExecutor(2, 3, 0,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        poolB.submit(() -> {
            System.out.println("4B......");
        });
        poolB.shutdown();
    }

    public static void useCompletableFuture() throws InterruptedException {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("5......");
            return "zhuZi";
        });
        // 需要阻塞，否则看不到结果
        Thread.sleep(1000);
    }

    public static void useThreadGroup() {
        ThreadGroup group = new ThreadGroup("groupName");

        new Thread(group, () -> {
            System.out.println("6-T1......");
        }, "T1").start();

        new Thread(group, () -> {
            System.out.println("6-T2......");
        }, "T2").start();

        new Thread(group, () -> {
            System.out.println("6-T3......");
        }, "T3").start();
    }
}

/**
 * 1.第一种方法 继承Thread 并重写run方法
 */
class ExtendsThread extends Thread {
    @Override
    public void run() {
        System.out.println("第一种创建线程的方法，继承Thread类，重写run方法");
    }
}

/**
 * 2.第一种方法 实现Ruunable接口，重写run方法
 */
class ImplementsRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("第一种创建线程的方法，实现Ruunable接口，重写run方法");
    }
}

class ImplementsCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "第三种创建线程的方法，实现Callable接口，重写call方法";
    }
}
