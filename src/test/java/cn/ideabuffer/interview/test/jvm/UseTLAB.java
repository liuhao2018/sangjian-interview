package cn.ideabuffer.interview.test.jvm;

/**
 * Created by sangjian on 2018/1/28.
 */
public class UseTLAB {

    public static void alloc(){
        byte[] b = new byte[2];
        b[0] = 1;
    }

    public static void main(String[] args) {
        long b = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            alloc();
        }
        long e = System.currentTimeMillis();
        System.out.println(e-b);
    }

}
