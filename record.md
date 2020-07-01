- netstat -aon | findstr 8080 # 查找端口占用
```
    TCP    0.0.0.0:8080           0.0.0.0:0              LISTENING       6300
    TCP    [::]:8080              [::]:0                 LISTENING       6300
```
- taskkill /f /pid 6300 # 杀死PID为6300的任务
- windows下启动zookeeper：
    - .\bin\zkServer.cmd
- grpc 编译选项：
    - ./gradlew -DsocksProxyHost=localhost -DsocksProxyPort=1080 installDist
- zookeeper启动：
    ```
    .\zookeeper\zookeeper1\bin\zkServer.cmd
    .\zookeeper\zookeeper2\bin\zkServer.cmd
    .\zookeeper\zookeeper3\bin\zkServer.cmd
    ```
- zookeeper客户端：
    `.\zookeeper\zookeeper1\bin\zkCli.cmd -server localhost:2181`

- 错误
    - Failed to execute goal org.xolstice.maven.plugins:protobuf-maven-plugin:0.6.1:compile (default-cli) on project DistributedKV: protoc did not exit cleanly. Review output for more information.
    - 是中文路径导致的问题，grpc对中文路径的支持并不友好……
    - 解决方案：管理员运行cmd.exe，执行`mklink /D "D:\program\DirtributeKV" D:\上海交通大学\大三下\分布式系统\Lab\DistributedKV`

zookeeper不是一个用来做高并发高性能的数据库，zookeeper一般只用来存储配置信息。
- Zookeeper中ZNode的节点类型:
    - PERSISTENT：持久化ZNode节点，一旦创建这个ZNode点存储的数据不会主动消失，除非是客户端主动的delete。
    - EPHEMERAL：临时ZNode节点，Client连接到Zookeeper Service的时候会建立一个Session，之后用这个Zookeeper连接实例创建该类型的znode，一旦Client关闭了Zookeeper的连接，服务器就会清除Session，然后这个Session建立的ZNode节点都会从命名空间消失。总结就是，这个类型的znode的生命周期是和Client建立的连接一样的。
    - PERSISTENT_SEQUENTIAL：顺序自动编号的ZNode节点，这种znoe节点会根据当前已近存在的ZNode节点编号自动加 1，而且不会随Session断开而消失。
    - EPEMERAL_SEQUENTIAL：临时自动编号节点，ZNode节点编号会自动增加，但是会随Session消失而消失
- Watcher数据变更通知


- Redis设计与实现：https://www.kancloud.cn/kancloud/redisbook/63825
- gRPC参考文档：https://www.grpc.io/docs/languages/java/quickstart/
- Zookeeper参考文档：https://zookeeper.apache.org/doc/current/zookeeperTutorial.html
- Zookeeper讲解：https://github.com/llohellohe/llohellohe.github.com/tree/master/readers/ZooKeeper
- Zookeeper设计：https://github.com/qiurunze123/zookeeperDesign
- 分布式存储知识体系：https://juejin.im/entry/5b0ca8f1518825158309ebec
- 分布式存储架构：https://zhuanlan.zhihu.com/p/55964292
- 分布式存储架构：https://zhuanlan.zhihu.com/p/27666295
- MIT 6.824 动态扩缩容/负载均衡的强一致容灾K/V集群：https://zhuanlan.zhihu.com/p/51049133
- Zookeeper服务发现：https://crossoverjie.top/2018/08/27/distributed/distributed-discovery-zk/
- 一致性哈希数据划分：https://zhuanlan.zhihu.com/p/37924185
- 数据迁移：https://cloud.tencent.com/developer/news/180072
- MIT 6.824课程主页：https://pdos.csail.mit.edu/6.824/index.html
- MIT 6.824：https://zhuanlan.zhihu.com/p/51049133
- 备份同步：https://www.cnblogs.com/glacierh/p/5734200.html
- redis主从复制：http://daoluan.net/redis-source-notes/redis-nei-gong-xin-fa/zhu-cong-fu-zhi.html

- 设计
    - 可用性：client加缓存
    - 可拓展性：改良的一致性哈希
    - 性能：

- 问题：
    1. 与zookeeper连接断开怎么办？轮流尝试每个zookeeper重复连接。
    2. Client如何知道Master的位置？Master使用公开的知名IP:Port。
    3. Master和Worker如何启动？master先启动和准备znode，worker等待master准备就绪再启动
    4. master如何监控worker的地址？worker创建临时节点，master监控并在本地存储映射表，有新增的补充，有删除的丢弃。
    5. /workers应该是临时的吗？如果master断开，所有的worker都要重新注册//TODO
    6. 数据如何划分？虚拟槽分区（改进的一致性哈希），划分会更加均匀。
    7. 如何进行数据迁移？懒加载，可用性保证（原子迁移：先迁移，再重定向，后删除//TODO，期间禁止写/双写）//TODO
    8. 数据迁移以多大粒度进行？以Slot为粒度进行KV的存储
    9. 冷启动如何分配数据？单独发送rpc要求直接负责所有slot，不需要问其他人获取数据
    10. rpc通道连接多久？client与master是长连接，其他连接完成后立即关闭
    11. zookeeper连接与rpc服务启动的前后顺序？先启动rpc服务，再连接向zookeeper报告自己启动完成
    12. 为什么不采用swift ring的异构备份？考虑到写少读多的情况，同构备份更简单。
    13. 是否持久化？//TODO
    14. 备份节点如何分配？每个主节点可以有不同的备节点，依照读取的热度来分配
    15. 主备如何同步？一对多同步，异步同步（强一致性的提供由Client保证，转发给主节点；更高的可用性会牺牲一定的一致性）
    16. 如何管理备份节点？指定工作组
    17. 是否支持缩容？暂不支持，假设一个组中的节点不可能同时挂掉，所以该功能不重要
    18. 如何同步？刚启动时全量同步，之后增量同步，先转发，如果都成功就成功；如果不成功，都回滚//TODO 两阶段提交 一半写入成功就返回？（没有返回结果之前，读到什么时不确定的）
    19. Primary如何知道其他Secondary的地址？
    20. 负载均衡？master对读进行roundrobin，写发给primary
    21. 启动流程？必须先竞选才能启动RPC服务，必须先准备好再注册自己
    22. 为什么不用读写锁？在整个数据节点粒度上的大读写锁，导致对并发的支持很差。所以使用原子计数器syncSeq定位各个操作的顺序。