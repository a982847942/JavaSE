package edu.nuaa.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/27 19:32
 */
class Bamboo {
    private int bambooCount = 0;
    private boolean flag = false;

    Lock lock = new ReentrantLock();
    Condition producerCondition = lock.newCondition();
    Condition consumerCondition = lock.newCondition();

    public void producerBamboo() {
        lock.lock(); // 获取锁资源
        try {
            while (flag) { // 如果有竹子
                try {
                    producerCondition.await(); // 挂起生产竹子的线程
//                    producerCondition.awaitUninterruptibly();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            bambooCount++; // 竹子数量+1
            System.out.println(Thread.currentThread().getName() + "....生产竹子，目前竹子数量：" + bambooCount);
            flag = true; // 竹子余量状态改为true
            consumerCondition.signal(); // 生产好竹子之后，唤醒消费竹子的线程
        } finally {
            lock.unlock(); // 释放锁资源
        }
    }

    public void consumerBamboo() {
        lock.lock(); // 获取锁资源
        try {
            while (!flag) { // 如果没有竹子
                try {
                    consumerCondition.await(); // 挂起消费竹子的线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            bambooCount--; // 竹子数量-1
            System.out.println(Thread.currentThread().getName() + "....消费竹子，目前竹子数量：" + bambooCount);
            flag = false; // 竹子余量状态改为false
            producerCondition.signal(); // 消费完成竹子之后，唤醒生产竹子的线程
        } finally {
            lock.unlock(); // 释放锁资源
        }
    }
}
/**------------------分割线--------------------**/
// 测试类
 public class ConditionDemo {
    public static void main(String[] args){
        Bamboo b = new Bamboo();
        Producer producer = new Producer(b);
        Consumer consumer = new Consumer(b);

        // 生产者线程组
        Thread t1 = new Thread(producer,"生产者-t1");
        Thread t2 = new Thread(producer,"生产者-t2");
        Thread t3 = new Thread(producer,"生产者-t3");

        // 消费者线程组
        Thread t4 = new Thread(consumer,"消费者-t4");
        Thread t5 = new Thread(consumer,"消费者-t5");
        Thread t6 = new Thread(consumer,"消费者-t6");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}
// 生产者
class Producer implements Runnable{
    private Bamboo bamboo;

    public Producer(Bamboo bamboo) {
        this.bamboo = bamboo;
    }
    @Override
    public void run() {
        for (;;){
            bamboo.producerBamboo();
        }
    }
}

// 生产者
class Consumer implements Runnable{
    private Bamboo bamboo;

    public Consumer(Bamboo bamboo) {
        this.bamboo = bamboo;
    }
    @Override
    public void run() {
        for (;;){
            bamboo.consumerBamboo();
        }
    }
}

