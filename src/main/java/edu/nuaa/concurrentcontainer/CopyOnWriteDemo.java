package edu.nuaa.concurrentcontainer;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/29 21:52
 */
public class CopyOnWriteDemo {
    /**
     * 关于写时复制的容器，优势比较明显，其内部充分运用了读写分离的思想提升了容器的整体并发吞吐量，
     * 以及避免了并发修改抛出的ConcurrentModificationException异常。但是也存在两个致命的缺陷：
     *
     * ①内存占用问题。因为CopyOnWrite容器每次在发生修改时都会复制一个新的数组，所以当数组数据过大时对内存消耗比较高。
     * ②数据不一致性问题。CopyOnWrite容器保证的是最终一致性，一条线程在执行修改操作，另一条线程在执行读取操作，
     * 读取的线程并不能看到最新的数据，就算修改操作执行了setArray()方法将指向改成了新数组，
     * 原本读取的线程也不能看到最新的数据。因为读取线程在执行读操作时并不是直接访问成员array完成的，
     * 而是通过getArray()方法的形式获取到的数组数据，在getArray()方法执行完成之后，读取数据的线程拿到的引用已经是旧数组的地址了，
     * 之后就算修改成员array的指向也不会影响get的访问。
     *
     * 还有一点值得注意：CopyOnWrite写时复制容器提升的只是读操作的吞吐量，而整个容器的写操作还是基于同一把独占锁保证的线程安全，
     * 所以如果需要频繁执行写操作的场景，并不适合用CopyOnWrite容器，同时还会因为复制带来的内存、时间开销导致性能下降。
     */
    public static void main(String[] args) {
        // 创建一个CopyOnWriteArrayList对象
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

        // 添加元素
        list.add(1);
        list.add(2);
        list.add(3);

        // 创建一个线程用于遍历元素
        Thread iteratorThread = new Thread(() -> {
            Iterator<Integer> iterator = list.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 创建一个线程用于修改元素
        Thread modifyThread = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(4);
            list.remove(1);
        });

        // 启动两个线程
        iteratorThread.start();
        modifyThread.start();
    }
}
