package edu.nuaa.volatile_Rule;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/26 15:27
 */
public class Singleton_05 {
    /**
     * volatile语义保证了 store 和 load的顺序
     */
    private static volatile Singleton_05 singleton05;
    private Singleton_05(){

    }

    public Singleton_05 getInstance(){
        if (singleton05 == null){
            synchronized (Singleton_05.class){
                if (singleton05 == null){
                    singleton05 = new Singleton_05();
                }
            }
        }
        return singleton05;
    }
}
