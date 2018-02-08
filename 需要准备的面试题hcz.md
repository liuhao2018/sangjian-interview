# part3

### BIO,NIO,AIO 的区别是什么？

* BIO： 传统的同步阻塞,一连接一线程,一个线程只能处理一个请求,如果遇到海量并发会导致资源耗尽.
* NIO： 同步非阻塞,一请求一线程.面向块的IO,提供了:1.Buffer,2.Channel,3,Selector ,把多个线程阻塞复用到一个Selector阻塞.
* AIO： 异步非阻塞,一有效请求一线程.不需要Selector对通道进行论询,即可实现异步读写.基于事件驱动,如果读操作完成会通过回调方法,通知

### redis 集群基础

* 所有的redis节点彼此互联(PING-PONG机制),内部使用二进制协议优化传输速度和带宽.
* 节点的fail是通过集群中超过半数的master节点检测失效时才生效.
* 客户端与redis节点直连,不需要中间proxy层.客户端不需要连接集群所有节点,连接集群中任何一个可用节点即可
* redis-cluster把所有的物理节点映射到[0-16383]slot上,cluster 负责维护node<->slot<->key.
* 如果存入一个值，按照redis cluster哈希槽的算法： CRC16('key')384 = 6782。 那么就会把这个key 的存储分配到对应的master上

### redis Cluster主从模式

* 如果进群超过半数以上master挂掉，无论是否有slave集群进入fail状态,所以集群中至少应该有奇数个节点，所以至少有三个节点，每个节点至少有一个备份节点,
* redis cluster 为了保证数据的高可用性，加入了主从模式，一个主节点对应一个或多个从节点，主节点提供数据存取，从节点则是从主节点拉取数据备份，当这个主节点挂掉后，就会有这个从节点选取一个来充当主节点，从而保证集群不会挂掉。

### Spring事务的传播机制

在 spring的 TransactionDefinition接口中一共定义了六种事务传播属性：
* PROPAGATION_REQUIRED -- 支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。 
* PROPAGATION_SUPPORTS -- 支持当前事务，如果当前没有事务，就以非事务方式执行。 
* PROPAGATION_MANDATORY -- 支持当前事务，如果当前没有事务，就抛出异常。 
* PROPAGATION_REQUIRES_NEW -- 新建事务，如果当前存在事务，把当前事务挂起。 
* PROPAGATION_NOT_SUPPORTED -- 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。 
* PROPAGATION_NEVER -- 以非事务方式执行，如果当前存在事务，则抛出异常。 
* PROPAGATION_NESTED -- 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则进行与PROPAGATION_REQUIRED类似的操作。 
前六个策略类似于EJB CMT，第七个（PROPAGATION_NESTED）是Spring所提供的一个特殊变量。 
它要求事务管理器或者使用JDBC 3.0 Savepoint API提供嵌套事务行为（如Spring的DataSourceTransactionManager） 

### 事务的隔离级别

数据库不考虑隔离存在的问题:1.脏读,2.不可重复读,3.虚读(幻读)
MySQL数据库为我们提供的四种隔离级别：

* Serializable (串行化)：可避免脏读、不可重复读、幻读的发生。
* Repeatable read (可重复读)：可避免脏读、不可重复读的发生。
* Read committed (读已提交)：可避免脏读的发生。
* Read uncommitted (读未提交)：最低级别，任何情况都无法保证。

在MySQL数据库中默认的隔离级别为Repeatable read (可重复读)。在MySQL数据库中查看当前事务的隔离级别：select @@tx_isolation;


### Full GC

堆内存划分为 Eden、Survivor 和 Tenured/Old 空间,从年轻代空间（包括 Eden 和 Survivor 区域）回收内存被称为 Minor GC，对老年代GC称为Major GC,而Full GC是对整个堆来说的.
触发Full GC
* System.gc()方法的调用  
* 老年代空间不足
* 永生区空间不足
* CMS GC时出现promotion failed和concurrent mode failure(对于采用CMS进行老年代GC的程序而言，尤其要注意GC日志中是否有promotion failed和concurrent mode failure两种状况，当这两种状况出现时可能会触发Full GC。)
* 堆中分配很大的对象 

### MySQL索引

[浅谈算法和数据结构: 十 平衡查找树之B树](http://www.cnblogs.com/yangecnu/p/introduce-b-tree-and-b-plus-tree.html)

[MySQL索引背后的数据结构及算法原理](http://blog.codinglabs.org/articles/theory-of-mysql-index.html)

[MySQL索引失效的几种情况](http://www.cnblogs.com/binyue/p/4058931.html)

### 公平锁与非公平锁

* 在公平的锁上，线程按照他们发出请求的顺序获取锁
* 但在非公平锁上，则允许‘插队’：当一个线程请求非公平锁时，如果在发出请求的同时该锁变成可用状态，那么这个线程会跳过队列中所有的等待线程而获得锁。

### PriorityQueue

[深入理解Java PriorityQueue](http://www.cnblogs.com/CarpenterLee/p/5488070.html)

[java多线程之优先队列PriorityBlockingQueue](http://blog.csdn.net/tianshi_kco/article/details/53026177)

### Java并发编程

[CountDownLatch、CyclicBarrier和 Semaphore](http://www.importnew.com/21889.html)


### Netty 之TCP粘包拆包问题

[Netty精粹之TCP粘包拆包问题](https://my.oschina.net/andylucc/blog/625315)

### java.nio.ByteBuffer

limit：所有对Buffer读写操作都会以limit变量的值作为上限。
position：代表对缓冲区进行读写时，当前游标的位置。
capacity：代表缓冲区的最大容量（一般新建一个缓冲区的时候，limit的值和capacity的值默认是相等的）。

### Google ProtoBuf

[Google ProtoBuf简介](http://blog.csdn.net/hailong0715/article/details/52016682)

[Google protobuf序列化以及反序列化](https://www.cnblogs.com/gdpuzxs/p/7081145.html)
