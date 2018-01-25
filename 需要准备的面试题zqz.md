# part2

## LinkedList(无界队列) 

可以在队头队尾加入、删除、访问元素

## LinkedHashMap

### 概述
继承HashMap；
双向链表；
内部有一个LinkedHashMap.Entry<K,V>来代替HashMap.Entry<K,V>, 重写了newNode方法，在put方法中创建新节点的时调用此方法返回一个LinkedHashMap.Entry<K,V>节点；

### 数据节点类
 	static class Entry<K,V> extends HashMap.Node<K,V> {
        Entry<K,V> before, after;//前后节点
        Entry(int hash, K key, V value, Node<K,V> next) {
            super(hash, key, value, next);
        }
    }
   两个成员变量before和after可以看出，这是一个双向链表 
    
### 重要的成员变量

//链表的头节点
transient LinkedHashMap.Entry<K,V> head;
//链表的尾节点
transient LinkedHashMap.Entry<K,V> tail;
//在get操作后，是否将该节点放入到队尾，默认值为false，可以通过构造方法设置其值public LinkedHashMap(int initialCapacity,float loadFactor,boolean accessOrder)
final boolean accessOrder;

### 重要的方法newNode
在put(入队)方法执行时调用
 	Node<K,V> newNode(int hash, K key, V value, Node<K,V> e) {
        LinkedHashMap.Entry<K,V> p =
            new LinkedHashMap.Entry<K,V>(hash, key, value, e);
        linkNodeLast(p);
        return p;
    }
linkNodeLast方法，负责将节点加入链表尾部，将新节点的before指向tail，然后将tail指向新节点

### 重要的方法afterNodeAccese

在get(读取)的时候调用（如果accessOrder==true则调用该方法，将当前节点移动到队尾tail）

	 void afterNodeAccess(Node<K,V> e) { // move node to last
        LinkedHashMap.Entry<K,V> last;
        if (accessOrder && (last = tail) != e) {
            LinkedHashMap.Entry<K,V> p =
                (LinkedHashMap.Entry<K,V>)e, b = p.before, a = p.after;
            p.after = null;
            if (b == null)
                head = a;
            else
                b.after = a;
            if (a != null)
                a.before = b;
            else
                last = b;
            if (last == null)
                head = p;
            else {
                p.before = last;
                last.after = p;
            }
            tail = p;
            ++modCount;
        }
    }


## LinkedBlockingQueue(有界队列)

### 队列大小
	如果不指定大小，可以认为是无界队列，因为队列默认大小为Integer.MAX_VALUE
  public LinkedBlockingQueue() {
        this(Integer.MAX_VALUE);
  }

### 数据节点类
	static class Node<E> {
        E item;//存储数据

        /**
         * One of:
         * - the real successor Node
         * - this Node, meaning the successor is head.next
         * - null, meaning there is no successor (this is the last node)
         */
        Node<E> next;//链表的下一个节点

        Node(E x) { item = x; }
    }
	可以看出这一个单向链表    
    
    
### 重要的成员变量
 
	//队列最大存放多少个对象  
    private final int capacity;  
    //当前队列中存的对象个数  
    private final AtomicInteger count = new AtomicInteger();  
    //队列头节点  
    transient Node<E> head;  
    //队列尾节点  
    private transient Node<E> last;  
    //出队时的独占锁，对队列头加锁   
    private final ReentrantLock takeLock = new ReentrantLock();  
    //队列是否是空的，用来阻塞take方法  
    private final Condition notEmpty = takeLock.newCondition();  
    //入队时的独占锁，对队列尾加锁  
    private final ReentrantLock putLock = new ReentrantLock();  
    //队列是否是慢的用来阻塞 put方法  
    private final Condition notFull = putLock.newCondition();

## HashMap

### 数据结构  

jdk1.8之前list + 链表
jdk1.8之后list + 链表/红黑树（当链表长度到8时，转化为红黑树）

### 扩容因子  
 
 默认0.75，也就是会浪费1/4的空间，达到扩容因子时，会将list扩容一倍，0.75 是时间与空间一个平衡值；
 
### 多线程修改HashMap 

多线程同时写入，同时执行扩容操作，多线程扩容可能死锁、丢数据；可以对HashMap 加入同步锁Collections.synchronizedMap(hashMap)，但是效率很低，因为该锁是互斥锁，同一时刻只能有一个线程执行读写操作，这时候应该使用ConcurrentHashMap

## 线程池

### 基础概念

core,maxPoolSize,keepalive
执行任务时
1. 如果线程池中线程数量 < core，新建一个线程执行任务；
2. 如果线程池中线程数量 >= core ,则将任务放入任务队列 （由此可见线程池的队列如果是无界队列，那么设置线程池最大数量是无效的）；
3. 如果线程池中线程数量 >= core 且 < maxPoolSize，则创建新的线程；
4. 如果线程池中线程数量 > core ,当线程空闲时间超过了keepalive时，则会销毁线程；


### 自带线程池的各种坑
1.  Executors.newFixedThreadPool(10);
固定大小的线程池：
它的实现new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
初始化一个指定线程数的线程池，其中corePoolSize == maximumPoolSize，使用LinkedBlockingQuene作为阻塞队列，当线程池没有可执行任务时，也不会释放线程。
由于LinkedBlockingQuene的特性，这个队列大小默认是Integer.MAX_VALUE，若消费不过来，会导致内存被任务队列占满，最终oom；

2. Executors.newCachedThreadPool();
缓存线程池：
它的实现new ThreadPoolExecutor(0,Integer.MAX_VALUE,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
初始化一个可以缓存线程的线程池，默认缓存60s，线程池的线程数可达到Integer.MAX_VALUE，即2147483647，内部使用SynchronousQueue作为阻塞队列；和newFixedThreadPool创建的线程池不同，newCachedThreadPool在没有任务执行时，当线程的空闲时间超过keepAliveTime，会自动释放线程资源，当提交新任务时，如果没有空闲线程，则创建新线程执行任务，会导致一定的系统开销，因为线程池的最大值了Integer.MAX_VALUE，会导致无限创建线程；所以，使用该线程池时，一定要注意控制并发的任务数，否则创建大量的线程会导致严重的性能问题;

3. Executors.newSingleThreadExecutor()
单线程线程池：
同newFixedThreadPool线程池一样，队列用的是LinkedBlockingQueue，队列大小默认是Integer.MAX_VALUE,可以无限的往里面添加任务，直到内存溢出；

## JMM

### happen before

### 指令重排序

1. cpu管线导致指令重排序
2. jvm编译器导致指令重排序

### as-if-serial语义

as-if-serial语义的意思指：不管怎么重排序（编译器和处理器为了提高并行度），（单线程）程序的执行结果不能被改变。编译器，runtime 和处理器都必须遵守as-if-serial语义。
为了遵守as-if-serial语义，编译器和处理器不会对存在数据依赖关系的操作做重排序，因为这种重排序会改变执行结果。但是，如果操作之间不存在数据依赖关系，这些操作可能被编译器和处理器重排序。

### 可见性

工作内存与主内存中数据不一致；

### 解决 指令重排序与可见性

#### 内存屏障：

在每个volatile写操作的前面插入一个StoreStore屏障。
在每个volatile写操作的后面插入一个StoreLoad屏障。
在每个volatile读操作的前面插入一个LoadLoad屏障。
在每个volatile读操作的后面插入一个LoadStore屏障。

#### volatile 

1. 防止重排序
2. 保证可见性

当写一个volatile变量时，JMM会把该线程对应的本地内存中的共享变量刷新到主内存。
当读一个volatile变量时，JMM会把该线程对应的本地内存置为无效。线程接下来将从主内存中读取共享变量。


#### synchronized 

#### final
对final域的读和写更像是普通的变量访问。对于final域，编译器和处理器要遵守两个重排序规则：
1. 在构造函数内对一个final域的写入，与随后把这个被构造对象的引用赋值给一个引用变量，这两个操作之间不能重排序。
2. 初次读一个包含final域的对象的引用，与随后初次读这个final域，这两个操作之间不能重排序。

http://ifeve.com/java-memory-model/

## spring

### 事务

### mvc

serverltDispatcher -- 》 doDispatcher --> 获取handler（controller，interceptor） --》获取handlerAdeptor（controller中的方法）

## JVM

### User user = new User()  做了什么操作，申请了哪些内存？

1. new User();  创建一个User对象，内存分配在堆上
2. User user;   创建一个引用，内存分配在栈上
3. =            将User对象地址赋值给引用

本问题可以扩展为懒汉单例模式怎么写才是正确的；



### CMS

