package edu.nuaa.volatile_Rule;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/26 15:22
 */
public class Singleton_04 {
    private static Singleton_04 singleton04;

    private Singleton_04(){

    }

    /**
     * 由于指令重排(处理器和编译器)的存在，下面的singleton04 == null判断可能会在
     * singleton04 = new Singleton_04();之前
     * 为什么这样说？ 因为复制操作不是一个原子操作
     * 1.分配内存空间
     * 2.初始化内存空间
     * 3.建立指针联系
     * 如果在 线程A执行顺序为 1 3 2 线程进行singleton04 == null就出错了，拿到一个没有初始化完成的错误对象！
     * 因此主要问题就是解决singleton04 == null这个读操作，和singleton04 = new Singleton_04()这个写操作
     * 在多线程情况下的重排序！！ 这就是可以使用内存屏障，最简单直接加volatile 也可以使用unsafe类的load load屏障 和
     * storeload屏障
     * @return
     */
    public Singleton_04 getInstance(){
        if (singleton04 == null){
            synchronized (Singleton_04.class){
                if (singleton04 == null){
                    singleton04 = new Singleton_04();
                }
            }
        }
        return singleton04;
    }
}
