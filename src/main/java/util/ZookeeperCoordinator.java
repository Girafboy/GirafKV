package util;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import server.Server;

import java.io.IOException;

public class ZookeeperCoordinator implements Coordinator {
    public void connect(String address, Watcher watcher) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper(address, 3000, watcher);
        String node = "/app1";
        Stat stat = zk.exists(node, false);
        if (stat == null) {
            String createResult = zk.create(node, "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(createResult);
        }
        byte[] b = zk.getData(node, false, stat);
        System.out.println(new String(b));
        zk.close();
    }
}
