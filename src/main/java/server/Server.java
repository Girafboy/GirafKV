package server;

import example.ZooKeeperHello;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class Server {

    // Args: address
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        if(args.length != 1){
            System.out.println("Args: address");
        }
        ZooKeeper zk = new ZooKeeper(args[0], 3000, new Server.DemoWatcher());
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

    static class DemoWatcher implements Watcher {
        public void process(WatchedEvent event) {
            System.out.println("----------->");
            System.out.println("path:" + event.getPath());
            System.out.println("type:" + event.getType());
            System.out.println("stat:" + event.getState());
            System.out.println("<-----------");
        }
    }
}
