package cn.ideabuffer.interview.test.thread;

import java.io.IOException;

/**
 * Created by sangjian on 2018/3/12 0012.
 */
public class DaemonThreadTest {

    public static void main(String[] args) throws IOException {
        Thread t = new Thread(() -> {
            while (true) {
                for (int i = 0; i < 1000; i++) {
                    System.out.println(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.setName("daemon-test");
//        t.setDaemon(true);
        t.start();
        System.in.read();

    }
}
