package example.servicediscovery;

import org.apache.zookeeper.*;

import java.io.IOException;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

/**
 * Created by wangxizhong on 17/2/27.
 */
public class Client implements Watcher {
    ZooKeeper zooKeeper;
    String hostPort;
    String name;

    Client(String hostPort) {
        this.hostPort = hostPort;
    }

    void startZK() throws IOException {
        zooKeeper = new ZooKeeper(hostPort, 15000, this);
    }

    String queueCommand(String command) throws Exception {
        while (true) {
            try {
                String name = zooKeeper.create(
                        "/tasks/task-",
                        command.getBytes(),
                        OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL_SEQUENTIAL
                );
                return name;
            } catch (KeeperException.NodeExistsException ex) {
                throw new Exception(name + "already appears to be running");
            } catch (KeeperException.ConnectionLossException ex) {

            }
        }
    }

    public void process(WatchedEvent event) {
        System.out.println(event);
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client("localhost:2181");
        client.startZK();
        String name = client.queueCommand("1");
        System.out.println("Created" + name);
    }

}
