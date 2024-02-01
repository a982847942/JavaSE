package edu.nuaa.threadpool;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/31 21:25
 */
public class ForkJoinPoolDemo{
    public static void main(String[] args) {
        testFor();
        testForkJoin();
    }

    // 测试for循环
    private static void testFor(){
        Instant startTime = Instant.now();
        List<Integer> list = new ArrayList<Integer>();
        for (int id = 1; id <= 1000*10000; id++) {
            // ....... 模拟从数据库根据id查询数据
            list.add(id);
        }
        Instant endTime = Instant.now();
        System.out.println("For循环耗时："+
                Duration.between(startTime,endTime).toMillis() + "ms");
    }

    // 测试ForkJoin框架
    private static void testForkJoin(){
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Instant startTime = Instant.now();
        List invoke = forkJoinPool.invoke(new IdByFindUpdate(1, 1000*10000));
        Instant endTime = Instant.now();
        System.out.println("ForkJoin耗时："+
                Duration.between(startTime,endTime).toMillis() + "ms");
    }
}

class IdByFindUpdate extends RecursiveTask<List> {
    private Integer startID;
    private Integer endID;

    private static final Integer THURSHOLD = 10000; // 临界值/阈值

    public IdByFindUpdate(Integer startID, Integer endID) {
        this.startID = startID;
        this.endID = endID;
    }

    @Override
    protected List<Integer> compute() {
        int taskSize = endID - startID;
        List<Integer> list = new ArrayList<Integer>();

        // 如果任务小于或等于拆分的最小阈值，那么则直接处理任务
        if (taskSize <= THURSHOLD) {
            for (int id = startID; id <= endID; id++) {
                // ....... 模拟从数据库根据id查询数据
                list.add(id);
            }
            return list;
        }
        // 任务fork拆分
        IdByFindUpdate leftTask = new IdByFindUpdate(startID,
                (startID + endID) / 2);
        leftTask.fork();
        IdByFindUpdate rightTask = new IdByFindUpdate(((startID
                + endID) / 2) + 1, endID);
        rightTask.fork();

        // 任务join合并
        list.addAll(leftTask.join());
        list.addAll(rightTask.join());

        return list;
    }
}

