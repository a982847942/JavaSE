package edu.nuaa.juc;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.stream.Stream;

/**
 * @author brain
 * @version 1.0
 * @date 2023/10/24 21:57
 */
public class Future_01 {
    static class A{
        public A() {
           new A();
        }
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        int a = 0;
        new FutureTask<>(()->{
            int c = 100;
        },a);
        new FutureTask<>(()->{
            int b = 10;
            return b;
        });
        Stream<String> stream = Stream.<String>of(new String[]{"a", "b", "c"});
        stream.map(String::toUpperCase).forEach(System.out::println);

        

//        new A();
        //这里的theUnsafe就是我们源码中的那个theUnsafe
//        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
//        theUnsafe.setAccessible(true);
//        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
//        A a = (A) unsafe.allocateInstance(A.class);
    }
}
