package edu.nuaa.aqs;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/28 20:16
 * @Description 一等多  数据聚合等
 */
public class CountDownLatchDemo_02 {
    static final CountDownLatch countDownLatch = new CountDownLatch(3);

    public static void main(String[] args) throws InterruptedException {
        Map data = new HashMap();
        for (int i = 1; i <= 3; i++) {
            final int page = i;
            new Thread(() -> {
                System.out.println("线程：" + Thread.currentThread().getName() +
                        "....读取分段数据：" + (page - 1) * 200 + "-" + page * 200 + "行");
                // 数据加入结果集：data.put();
                countDownLatch.countDown();
            }, "T" + i).start();
        }
        countDownLatch.await();
        System.out.println("线程：" + Thread.currentThread().getName()
                + "....对数据集：data进行处理");

/*
运行结果：
    线程：T1....读取分段数据：0-200行
    线程：T2....读取分段数据：200-400行
    线程：T3....读取分段数据：400-600行

    线程main....对数据集：data进行处理
*/

    }
}
