package edu.nuaa.volatile_Rule;

/**
 * @author brain
 * @Description 懒汉式 + 锁
 * @version 1.0
 * @date 2024/1/26 15:20
 */
public class Singleton_03 {
    private static Singleton_03 singleton_03;
    private Singleton_03(){

    }

    public Singleton_03 getInstance(){
        /**
         * 可能锁力度太大 可以减小锁力度，个人感觉没必要。
         */
        synchronized (Singleton_03.class){
            if (singleton_03 == null){
                singleton_03 = new Singleton_03();
            }
            return singleton_03;
        }
    }
}
