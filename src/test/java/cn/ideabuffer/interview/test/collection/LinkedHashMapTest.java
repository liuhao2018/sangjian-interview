package cn.ideabuffer.interview.test.collection;

import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sangjian on 2018/1/24 0024.
 */
public class LinkedHashMapTest {

    /**
     * 正常遍历，不会出现异常
     */
    @Test
    public void accessOrderEntrySetTest() {
        Map<String, Integer> map = new LinkedHashMap<>(16, 0.75f, true);
        map.put("s1", 1);
        map.put("s2", 2);
        map.put("s3", 3);
        map.put("s4", 4);
        map.put("s5", 5);
        map.put(null, 9);
        map.put("s6", 6);
        map.put("s7", 7);
        map.put("s8", 8);
        map.put(null, 11);
        map.get("s6");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    /**
     * 执行输出：
     * <pre>
     * s1->1
     * java.util.ConcurrentModificationException
     *      at java.util.LinkedHashMap$LinkedHashIterator.nextNode(LinkedHashMap.java:719)
     *      at java.util.LinkedHashMap$LinkedKeyIterator.next(LinkedHashMap.java:742)
     *      at cn.ideabuffer.interview.test.collection.LinkedHashMapTest.accessOrderIteratorTest(LinkedHashMapTest.java:49)
     *      ...
     * </pre>
     * LinkedHashMap非但没有排序，反而程序出现了异常，这是为什么呢？
     * ConcurrentModificationException异常一般会在集合迭代过程中被修改事抛出。不仅仅是LinkedHashMap,所有的集合都不允许在迭代器模式中修改集合的结构。
     * 一般认为，put()、remove()方法会修改集合的结构，因此不能在迭代器中使用。但是，这段代码中并没有出现类似修改集合结构的代码，为何也会发生这样的问题？
     * 问题就出在get()方法上。
     * 虽然一般认为get()方法是只读的，但是当前的LinkedHashMap缺工作在按照元素访问顺序排序的模式中，get()方法会修改LinkedHashMap中的链表结构，以便将最近访问的元素放置到链表的末尾，
     * 因此，这个操作便引起了这个错误。所以，当LinkedHashMap工作在这个模式时，不能再迭代器中使用get()操作。Map的遍历建议使用entrySet的方式。
     */
    @Test
    public void accessOrderIteratorTest() {
        Map<String, Integer> map = new LinkedHashMap<>(16, 0.75f, true);
        map.put("s1", 1);
        map.put("s2", 2);
        map.put("s3", 3);
        map.put("s4", 4);
        map.put("s5", 5);
        map.put(null, 9);
        map.put("s6", 6);
        map.put("s7", 7);
        map.put("s8", 8);
        map.put(null, 11);
        map.get("s6");
        for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext(); ) {
            String name = iterator.next();
            System.out.println(name + "->" + map.get(name));
        }
    }

}
