package cn.ideabuffer.interview.test.pattern;

import java.io.IOException;

/**
 * Created by sangjian on 2018/3/12 0012.
 */
public class Singleton {
    private static class SingletonHolder{
        private static final Singleton instance = new Singleton();
    }
    private Singleton(){
        System.out.println("#####################");
    }
    public static final Singleton getInsatance(){
        return SingletonHolder.instance;
    }

    public static void main(String[] args) throws IOException {
        System.in.read();
    }
}
