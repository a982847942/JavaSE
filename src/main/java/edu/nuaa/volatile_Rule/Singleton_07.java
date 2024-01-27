package edu.nuaa.volatile_Rule;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/26 15:38
 */
public class Singleton_07 {
    public static void main(String[] args) {
        Singleton instance = Singleton.INSTANCE;

    }
}
enum Singleton{
    INSTANCE;
}
