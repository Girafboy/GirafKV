package example.servicediscovery;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Date;

/**
 * Created by wangxizhong on 17/2/27.
 */
public class AdminClient implements Watcher {
    ZooKeeper zooKeeper;
    String hostport;

    AdminClient(String hostport) {
        this.hostport = hostport;
    }

    void start() throws IOException {
        zooKeeper = new ZooKeeper(hostport, 15000, this);
    }

    void listState() throws KeeperException, InterruptedException {
        try {
            Stat stat = new Stat();
            byte masterData[] = zooKeeper.getData("/master", false, stat);
            Date startDate = new Date(stat.getCtime());
            System.out.println("Master:" + new String(masterData) + " since " + startDate);
        } catch (KeeperException.NoNodeException ex) {
            System.out.println("No Master");
        }
        System.out.println("Workers:");
        for (String worker : zooKeeper.getChildren("/workers", false)) {
            byte[] data = zooKeeper.getData("/workers/" + worker, false, null);
            String state = new String(data);
            System.out.println("\t" + worker + ": " + state);
        }
        System.out.println("Tasks:");
        for (String task : zooKeeper.getChildren("/tasks", false)) {
            System.out.println("\t" + task);
        }
    }

    public void process(WatchedEvent event) {
        System.out.println(event);
    }

    public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
        AdminClient adminClient = new AdminClient("localhost:2181");
        adminClient.start();
        adminClient.listState();
    }
}
