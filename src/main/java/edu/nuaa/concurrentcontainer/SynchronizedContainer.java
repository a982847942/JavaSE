package edu.nuaa.concurrentcontainer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/29 19:46
 */
public class SynchronizedContainer {
    public static void main(String[] args) {
        Map map = new HashMap<String,Object>();
        Map syncMap = Collections.synchronizedMap(map);
        /**
         *这两个线程不能真正意义上并发，因为syncMap会对map加锁操作
         */
        new Thread(()->{
            syncMap.put("竹子","熊猫");
        }).start();
        new Thread(()->{
            syncMap.get("xxx");
        }).start();

    }
}
