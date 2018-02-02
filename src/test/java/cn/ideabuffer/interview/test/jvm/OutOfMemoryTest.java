package cn.ideabuffer.interview.test.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sangjian on 2018/1/31.
 */
public class OutOfMemoryTest {

    static List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        while(true){
            list.add("");
        }
    }

}
