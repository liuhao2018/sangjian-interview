package cn.ideabuffer.interview.test.jvm;

/**
 * 锁消除测试
 * -server
 * -XX:+DoEscapeAnalysis
 * -XX:+EliminateLocks
 * -Xcomp
 * -XX:-BackgroundCompilation
 * -XX:BiasedLockingStartupDelay=0
 * Created by sangjian on 2018/1/30.
 */
public class LockEliminate {

    private static final int CIRCLE = 2000000;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < CIRCLE; i++) {
            createStringBuffer("JVM", "Diagnosis");
        }
        long bufferCost = System.currentTimeMillis() - start;
        System.out.println("createStringBuffer: " + bufferCost + " ms");
    }

    public static String createStringBuffer(String s1, String s2) {
        StringBuffer sb = new StringBuffer();
        sb.append(s1);
        sb.append(s2);
        return sb.toString();
    }

}
