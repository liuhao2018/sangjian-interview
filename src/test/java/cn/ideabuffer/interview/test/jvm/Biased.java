package cn.ideabuffer.interview.test.jvm;

import java.util.List;
import java.util.Vector;

/**
 * -XX:+UseBiasedLocking
 * -XX:BiasedLockingStartupDelay=0
 * -client
 * -Xmx512m -Xms512m
 * Created by sangjian on 2018/1/30.
 */
public class Biased {

    public static List<Integer> numberList = new Vector<>();

    public static void main(String[] args) {
        long b = System.currentTimeMillis();
        int count = 0;
        int startnum = 0;
        while (count < 10000000) {
            numberList.add(startnum);
            startnum += 2;
            count++;
        }
        long e = System.currentTimeMillis();
        System.out.println(e - b);
    }

}
