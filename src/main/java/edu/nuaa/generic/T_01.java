package edu.nuaa.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/10/25 19:05
 */
public class T_01 {
    public static void main(String[] args) {
       test_01();
    }

    private static void test_01(){
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List copy = list;
        copy.add("a");
        List<Integer> list2 = copy;
        // TODO: 2023/10/25  为什么不报错
        System.out.println(list.get(5));
    }
   private static void test_02(){
       List<Integer> list = Collections.checkedList(Arrays.asList(1, 2, 3, 4, 5), Integer.class);
       List copy = list;
       // Exception in thread "main" java.lang.ClassCastException: Attempt to insert class java.lang.String element into collection with element type class java.lang.Integer
       copy.add("a");
   }

   private static void test_03(){
       // 创建一个 ArrayList<Integer> 集合
       ArrayList<Integer> integerList = new ArrayList<>();

       // 添加一个 Integer 对象
       integerList.add(new Integer(123));

       // “向上转型”为 ArrayList<Number>
//       ArrayList<Number> numberList = integerList;

       // 添加一个 Float 对象，Float 也是 Number 的子类，编译器不报错
//       numberList.add(new Float(12.34));

       // 从 ArrayList<Integer> 集合中获取索引为 1 的元素（即添加的 Float 对象）：
       Integer n = integerList.get(1); // ClassCastException，运行出错
   }
}
