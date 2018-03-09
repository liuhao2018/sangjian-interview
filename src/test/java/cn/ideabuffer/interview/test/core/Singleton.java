package cn.ideabuffer.interview.test.core;

/**
 * Created by sangjian on 2018/3/8 0008.
 */
public class Singleton {

    private static Singleton singleton = new Singleton();
    private static int count1;
    private static int count2 = 0;

    private Singleton(){
        count1++;
        count2++;
    }

    public static Singleton getInstance(){
        return singleton;
    }

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        System.out.println(singleton.count1);
        System.out.println(singleton.count2);
    }
}
