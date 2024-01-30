package edu.nuaa.concurrentcontainer;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/29 19:52
 */
public class ArrayBlockingQueueDemo {
    // 创建一个阻塞队列容器
//    private static ArrayBlockingQueue<String> arrayBlockingQueue
//            = new ArrayBlockingQueue<String>(5);
    /**
     * LinkedBlockingQueue底层使用单向链表结构以及双锁实现，我为了简单则将其称呼为“读锁”、“写锁”，
     * 但是大家不要被误导，该“读锁”并非真正意义上的读，因为如果只是读操作的话是不需要加锁的，
     * 而队列的take方法在读取了元素之后还需移除该元素，所以里面也涉及到了写的操作，自然也需要加锁保证线程安全。
     * 准确来说，我所谓的“读/写锁”实际上是指“take/put”锁。
     */
    private static LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
//    PriorityBlockingQueue priorityBlockingQueue = new PriorityBlockingQueue();
//    DelayQueue delayQueue = new DelayQueue();
//    SynchronousQueue synchronousQueue = new SynchronousQueue();
//    LinkedTransferQueue linkedTransferQueue = new LinkedTransferQueue();
//    LinkedBlockingDeque linkedBlockingDeque = new LinkedBlockingDeque();

    public static void main(String[] args) {
        //创建生产者与消费者任务
//        Producer producerTask = new Producer(arrayBlockingQueue);
//        Consumer consumerTask = new Consumer(arrayBlockingQueue);
        Producer producerTask = new Producer(linkedBlockingQueue);
        Consumer consumerTask = new Consumer(linkedBlockingQueue);
        // 生产者线程组
        Thread T1 = new Thread(producerTask, "T1");
        Thread T2 = new Thread(producerTask, "T2");

        // 消费者线程组
        Thread Ta = new Thread(consumerTask, "Ta");
        Thread Tb = new Thread(consumerTask, "Tb");

        T1.start();
        T2.start();
        Ta.start();
        Tb.start();
    }

    // 生产者
    static class Producer implements Runnable {
        private BlockingQueue<String> blockingQueue;

        private Producer(BlockingQueue<String> b) {
            this.blockingQueue = b;
        }

        @Override
        public void run() {
            for (; ; )
                producer();
        }

        private void producer() {
            String task = "竹子-" + UUID.randomUUID().toString();
            try {
                blockingQueue.put(task);
                System.out.println(Thread.currentThread().getName()
                        + "生产任务：" + task);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //消费者
    static class Consumer implements Runnable {
        private BlockingQueue<String> blockingQueue;

        private Consumer(BlockingQueue<String> b) {
            this.blockingQueue = b;
        }

        @Override
        public void run() {
            for (; ; )
                consumer();
        }

        private void consumer() {
            try {
                Thread.sleep(200);
                String task = blockingQueue.take();
                System.out.println(Thread.currentThread().getName()
                        + "消费任务：" + task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /* *
     * 执行结果：
     *    T1生产任务：竹子-f1ae18fc-de1c-49f2-b9c0-3b3c45ae2931
     *    Tb消费任务：竹子-46b45b67-4a1b-481a-80eb-3627d0c56a15
     *    T2生产任务：竹子-46b45b67-4a1b-481a-80eb-3627d0c56a15
     *    Ta消费任务：竹子-f1ae18fc-de1c-49f2-b9c0-3b3c45ae2931
     *    .........
     * */

}
