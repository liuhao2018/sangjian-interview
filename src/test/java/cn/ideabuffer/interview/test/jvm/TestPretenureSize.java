package cn.ideabuffer.interview.test.jvm;

import org.junit.Test;

/**
 * Created by sangjian on 2018/1/26.
 */
public class TestPretenureSize {

    private static final int _1MB = 1024 * 1024;

    /**
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * -XX:PretenureSizeThreshold=3145728 -XX:+UseSerialGC
     * 这里要指定-XX:+UseSerialGC
     * 注意:PretenureSizeThreshold参数只对Serial和ParNew两款收集器有效，Parallel Scavenge收集器不认识这个参数，Parallel Scavenge收集器一般并不需要设置。
     * 如果遇到必须使用此参数的场合，可以考虑ParNew加CMS的收集器组合。
     */
    @Test
    public void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[4 * _1MB];  //直接分配在老年代中
    }

}
