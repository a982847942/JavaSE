package edu.nuaa.volatile_Rule;

/**
 * @author brain
 * @Description 静态内部类
 * @version 1.0
 * @date 2024/1/26 15:29
 */
public class Singleton_06 {
    private static class SingletonHolder{
        private static final Singleton_06 INSTANCE = new Singleton_06();
    }
    private Singleton_06(){}

    public static final Singleton_06 getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
