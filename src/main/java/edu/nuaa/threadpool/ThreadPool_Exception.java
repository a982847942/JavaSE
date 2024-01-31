package edu.nuaa.threadpool;

import java.util.concurrent.*;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/31 9:16
 */
public class ThreadPool_Exception extends ThreadPoolExecutor {
    public ThreadPool_Exception(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
//        int i = 1/ 0;
    }




    public static void main(String[] args) {
        test_shutdown();
    }
    static void test_shutdown(){
        ThreadPool_Exception threadPoolException = new ThreadPool_Exception(1, 1, 10, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                            @Override
                            public void uncaughtException(Thread t, Throwable e) {
                                System.out.println(Thread.currentThread() + "出现异常" + e.getMessage() );
//                                System.out.println("原因:" + e.getCause() + "提示:" + e.getMessage());
//                                e.printStackTrace();
                            }
                        });
                        return thread;
                    }
                }, new CallerRunsPolicy());
        threadPoolException.allowCoreThreadTimeOut(true);
        System.out.println(threadPoolException.getActiveCount());
        threadPoolException.execute(()->{
            System.out.println("运行结束");
        });
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(threadPoolException.getActiveCount());
        System.out.println(threadPoolException.getPoolSize());
    }

    static void test_Exception(){
        ThreadPool_Exception threadPoolException = new ThreadPool_Exception(1, 1, 0, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                            @Override
                            public void uncaughtException(Thread t, Throwable e) {
                                System.out.println(Thread.currentThread() + "出现异常" + e.getMessage() );
//                                System.out.println("原因:" + e.getCause() + "提示:" + e.getMessage());
//                                e.printStackTrace();
                            }
                        });
                        return thread;
                    }
                }, new CallerRunsPolicy());
        threadPoolException.execute(()->{
            System.out.println(1);
//            try {
//                int i = 1 / 0;
//            }catch (RuntimeException exception){
//                System.out.println("出异常了");
//            }
            System.out.println("第一个任务:" + Thread.currentThread());
        });
        threadPoolException.execute(()->{
            System.out.println(1);
            System.out.println("第二个任务:" + Thread.currentThread());
        });
        threadPoolException.execute(()->{
            System.out.println(1);
            System.out.println("第三个任务:" + Thread.currentThread());
        });
        threadPoolException.execute(()->{
            System.out.println(1);
            System.out.println("第四个任务:" + Thread.currentThread());
        });
    }
}
