package example.servicediscovery;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Random;

/**
 * Created by wangxizhong on 17/2/26.
 */
public class Worker implements Watcher {

    ZooKeeper zooKeeper;
    String hostPort;
    String serverId = Integer.toHexString(new Random().nextInt());
    String status;
    String name;

    Worker(String hostPort) {
        this.hostPort = hostPort;
    }

    void startZK() throws IOException {
        zooKeeper = new ZooKeeper(hostPort, 15000, this);
    }

    public void process(WatchedEvent event) {
        System.out.println(event.toString() + "," + hostPort);
    }

    void register() {
        zooKeeper.create(
                "/workers/worker-" + serverId,
                "Idle".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                stringCallback,
                null
        );
    }

    AsyncCallback.StringCallback stringCallback = new AsyncCallback.StringCallback() {
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    register();
                    break;
                case OK:
                    System.out.println("Registered sccessfully:" + serverId);
                    break;
                case NODEEXISTS:
                    System.out.println("Already registered:" + serverId);
                    break;
                default:
                    System.out.println("Something went wrong:" +
                            KeeperException.create(KeeperException.Code.get(rc), path));
                    break;
            }
        }
    };
    AsyncCallback.StatCallback statCallback = new AsyncCallback.StatCallback() {
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    updateStatus((String) ctx);
                    break;
            }
        }
    };
    synchronized  private void updateStatus(String status){
        if(status.equals(this.status)){
            name = "worker-" + serverId;
            zooKeeper.setData("/workers/"+name,status.getBytes(),-1,statCallback,status);
        }
    }
    public void setStatus(String status){
        this.status=status;
        updateStatus(status);
    }

    public static void main(String [] args) throws InterruptedException, IOException {
        Worker worker=new Worker("localhost:2181");
        worker.startZK();
        worker.register();
        worker.setStatus("working");
        Thread.sleep(300000);
    }
}
