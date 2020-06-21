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


- 错误
    - Failed to execute goal org.xolstice.maven.plugins:protobuf-maven-plugin:0.6.1:compile (default-cli) on project DistributedKV: protoc did not exit cleanly. Review output for more information.
    - 是中文路径导致的问题，grpc对中文路径的支持并不友好……
    - 解决方案：管理员运行cmd.exe，执行`mklink /D "D:\program\DirtributeKV" D:\上海交通大学\大三下\分布式系统\Lab\DistributedKV`

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

- 设计
    - 