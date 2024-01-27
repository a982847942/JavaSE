package edu.nuaa.volatile_Rule;

/**
 * @author brain
 * @Description 饿汉式
 * @version 1.0
 * @date 2024/1/26 15:12
 */
public class Singleton_01 {
    /**
     * 类变量在类加载的初始化阶段调用clinit初始化，不管有多少个实例只初始化一次
     */
    private static final Singleton_01 SINGLETON_01 = new Singleton_01();

//    static {
//        SINGLETON_01 = new Singleton_01();
//    }

    /**
     * 私有化 构造器
     */
    private Singleton_01(){
    }
    public static Singleton_01 getInstance(){
        return SINGLETON_01;
    }
}
