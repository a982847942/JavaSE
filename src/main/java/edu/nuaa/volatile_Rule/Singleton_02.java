package edu.nuaa.volatile_Rule;

/**
 * @author brain
 * @Description 懒汉式
 * @version 1.0
 * @date 2024/1/26 15:16
 */
public class Singleton_02 {
    private static Singleton_02 singleton_02;
    private Singleton_02(){

    }

    public Singleton_02 getInstance(){
        /**
         * 操作非原子  多线程不安全
         */
        if (singleton_02 == null){
           singleton_02 = new Singleton_02();
        }
        return singleton_02;
    }
}
