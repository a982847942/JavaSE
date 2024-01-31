package edu.nuaa.concurrentcontainer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/30 8:53
 */
public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
//        Map<String,String> map = new HashMap<>();
//        map.put("user1:name","css");
//        map.put("user2:name","zl");
//        System.out.println(map.get("user1:name"));


        Map<String,Integer> concurrentMap = new ConcurrentHashMap<>();
        /**
         * if (key == null || value == null) throw new NullPointerException();
         */
        concurrentMap.put("user1:age",24);
        concurrentMap.put("user2:age",23);
        System.out.println(concurrentMap.get("user1:age"));
        System.out.println(concurrentMap.size());
    }
}
