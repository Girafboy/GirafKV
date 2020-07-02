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
    或者直接双击zkstart.cmd
- zookeeper客户端：
    `.\zookeeper\zookeeper1\bin\zkCli.cmd -server localhost:2181`

- 错误
    - Failed to execute goal org.xolstice.maven.plugins:protobuf-maven-plugin:0.6.1:compile (default-cli) on project DistributedKV: protoc did not exit cleanly. Review output for more information.
    - 是中文路径导致的问题，grpc对中文路径的支持并不友好……
    - 解决方案：管理员运行cmd.exe，执行`mklink /D "D:\program\DirtributeKV" D:\上海交通大学\大三下\分布式系统\Lab\DistributedKV`

- 参考资料
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
    - 2PC: https://juejin.im/post/5ea1234be51d4547106e1d13
    - 分布式一致性协议：https://matt33.com/2018/07/08/distribute-system-consistency-protocol/
    - 主从同步：https://blog.csdn.net/tiankong_/article/details/78008736

