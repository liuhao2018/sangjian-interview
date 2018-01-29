package cn.ideabuffer.interview.test.jvm;

import java.util.HashMap;
import java.util.Map;

/**
 * -Xmx32m -Xms32m -XX:+UseSerialGC -XX:+PrintGCDetails -XX:PretenureSizeThreshold=1000
 * tenured generation   total 21888K, used 16K [0x00000007beaa0000, 0x00000007c0000000, 0x00000007c0000000)
 * 从GC日志中可以看出，有一部分分配到了TLAB
 * Created by sangjian on 2018/1/28.
 */
public class PretenureSizeThreshold {

    private static final int _1K = 1024;

    public static void main(String[] args) {
        Map<Integer, byte[]> map = new HashMap<>();

        for (int i = 0; i < 5 * _1K; i++) {
            byte[] b = new byte[_1K];
            map.put(i, b);
        }
    }

}
