package edu.nuaa.juc;

import java.util.concurrent.*;

/**
 * @author brain
 * @version 1.0
 * @date 2023/10/30 11:37
 */
public class Semaphore_01 {
    public static void main(String[] args) {
        // 自定义线程池(后续文章会详细分析到)
        // 环境：四核四线程CPU 任务阻塞系数0.9
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                4*2, 40,
                30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1024*10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        // 设置信号量同一时刻最大线程数为3
        final Semaphore semaphore = new Semaphore(3);
        // 模拟100个对账请求
        for (int index = 0; index < 100; index++) {
            final int serial = index;
            threadPool.execute(()->{
                try {
                    // 使用acquire()获取许可
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() +
                            "线程成功获取许可！请求序号: " + serial);
                    // 模拟数据库IO
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }  finally {
                    // 临界资源访问结束后释放许可
                    semaphore.release();
                }
            });
        }
        // 关闭线程池资源
        threadPool.shutdown();
    }
}
