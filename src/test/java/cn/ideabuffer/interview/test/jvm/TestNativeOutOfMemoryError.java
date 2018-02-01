package cn.ideabuffer.interview.test.jvm;

import java.util.concurrent.CountDownLatch;

/**
 * 测试：java.lang.OutOfMemoryError: unable to create new native thread
 * Created by sangjian on 2018/1/31.
 */
public class TestNativeOutOfMemoryError {

    public static void main(String[] args) {

        for (int i = 0;; i++) {
            System.out.println("i = " + i);
            new Thread(new HoldThread()).start();
        }
    }

}

class HoldThread extends Thread {
    CountDownLatch cdl = new CountDownLatch(1);

    public HoldThread() {
        this.setDaemon(true);
    }

    public void run() {
        try {
            cdl.await();
        } catch (InterruptedException e) {
        }
    }
}
