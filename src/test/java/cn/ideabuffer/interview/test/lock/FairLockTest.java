package cn.ideabuffer.interview.test.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试：公平锁,非公平锁
 */

public class FairLockTest {

    private ReentrantLock lock ;

    public FairLockTest(boolean isFair) {
        lock = new ReentrantLock(isFair);
    }

    public void service() {
        try {
            lock.lock();
            System.out.println("ThreadName=" + Thread.currentThread().getName() + " 获得锁定");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        FairLockTest fairLockTest = new FairLockTest(true);  //改为false就为非公平锁了
        Runnable runnable = new Runnable() {
            public void run() {
                System.out.println("线程： " + Thread.currentThread().getName()+  " 运行了 " );
                fairLockTest.service();
            }
        };

        Thread[] threads = new Thread[10];

        for (int i=0; i<10; i++) {
            threads[i] = new Thread(runnable);
        }
        for (int i=0; i<10; i++) {
            TimeUnit.SECONDS.sleep(1);
            threads[i].start();
        }
    }

}
