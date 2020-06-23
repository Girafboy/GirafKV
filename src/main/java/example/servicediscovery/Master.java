package example.servicediscovery;

import example.servicediscovery.ChildrenCache;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import javax.swing.plaf.PanelUI;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

/**
 * Created by wangxizhong on 17/2/26.
 */
public class Master implements Watcher {
    private ZooKeeper zooKeeper;
    private String hostPort;
    private String serverId = Integer.toHexString(new Random().nextInt());
    public boolean isLeader = false;
    public Logger logger = Logger.getLogger(getClass());
    private MasterStates state;
    private ChildrenCache workersCache;

    // Master状态
    private enum MasterStates {
        RUNNING,
        ELECTED,
        NOTELECTED
    }


    public Master(String hostPort) {
        this.hostPort = hostPort;
    }

    void startZK() throws IOException {
        zooKeeper = new ZooKeeper(hostPort, 15000, this);
    }

    public void process(WatchedEvent event) {
        System.out.println(event);
    }

    void stopZK() throws InterruptedException {
        zooKeeper.close();
    }

    AsyncCallback.DataCallback dataCallback = new AsyncCallback.DataCallback() {
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    checkMaster();
                    return;
                case NONODE:
                    runForMaster();
                    return;
            }
        }
    };

    void checkMaster() {
        zooKeeper.getData("/master", false, dataCallback, null);
    }

    void runForMaster() {
        zooKeeper.create("/master",
                serverId.getBytes(),
                OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                masterCreateCallback,
                null
        );

    }

    //Async
    AsyncCallback.StringCallback masterCreateCallback = new AsyncCallback.StringCallback() {
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    checkMaster();
                    return;
                case OK:
                    state = MasterStates.ELECTED;
                    System.out.println("Im leader!!!!!");
                    break;
                case NODEEXISTS:
                    state = MasterStates.NOTELECTED;
                    masterExists();
                default:
                    state = MasterStates.NOTELECTED;
            }
        }
    };
    AsyncCallback.StatCallback masterStateCallback = new AsyncCallback.StatCallback() {
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    masterExists();
                    return;
                case OK:
                    //state = MasterStates.ELECTED;
                    if (stat == null) {
                        state = MasterStates.RUNNING;
                        // 选举自己为Master
                        runForMaster();
                    }
                    break;
                default:
                    checkMaster();
                    break;
            }
        }
    };

    void masterExists() {
        zooKeeper.exists("/master",
                masterExistsWatcher,
                masterStateCallback,
                null
        );
    }

    Watcher masterExistsWatcher = new Watcher() {
        public void process(WatchedEvent event) {
            System.out.println("***************" + event);
            if (event.getType() == Event.EventType.NodeDeleted) {
                assert "/master".equals(event.getPath());
                runForMaster();
            }
        }
    };

    //register parent
    public void bootstrap() {
        createParent("/workers", new byte[0]);
        createParent("/assign", new byte[0]);
        createParent("/tasks", new byte[0]);
        createParent("/status", new byte[0]);
    }

    void createParent(String path, byte[] data) {
        zooKeeper.create(path,
                data,
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT,
                masterCreateCallback,
                data);
    }

    /*AsyncCallback.StringCallback createParentCallback = new AsyncCallback.StringCallback() {
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    createParent(path, (byte[]) ctx);
                    break;
                case OK:
                    System.out.println("Parent created:"+path);
                    logger.info("Parent created");
                    break;
                case NODEEXISTS:
                    System.out.println("Parent already registered:" + path);
                    logger.warn("Parent already registered:" + path);
                    break;
                default:
                    System.out.println("Something went wrong:" +
                            KeeperException.create(KeeperException.Code.get(rc), path));
                    logger.error("Something went wrong:" +
                            KeeperException.create(KeeperException.Code.get(rc), path));
            }
        }
    };*/
    private Watcher workersChangeWatcher=new Watcher() {
        public void process(WatchedEvent event) {
            if(event.getType()==Event.EventType.NodeChildrenChanged){
                assert  "/workers".equals(event.getPath());
                getWorkers();
            }
        }
    };
    private AsyncCallback.ChildrenCallback workersGetChldrenCallback=new AsyncCallback.ChildrenCallback() {
        public void processResult(int rc, String path, Object ctx, List<String> children) {
            switch (KeeperException.Code.get(rc)){
                case CONNECTIONLOSS:
                    getWorkers();
                    break;
                case OK:
                    reassignAndSet(children);
                    break;
                default:

            }
        }
    };
    public void getWorkers(){
        zooKeeper.getChildren("/workers",workersChangeWatcher,workersGetChldrenCallback,null);
    }
    private void reassignAndSet(List<String> children) {
        List<String> toProcess = null;
        if(workersCache == null) {
            // 初始化缓存
            workersCache = new ChildrenCache(children);
        } else {
            logger.info( "Removing and setting" );
            // 检查某些被删除的Worker
            toProcess = workersCache.removedAndSet(children);
        }
        if(toProcess != null) {
            for(String worker : toProcess) {
                // 重新分配删除的Worker的任务
                getAbsentWorkerTasks(worker);
            }
        }
    }
    private void getAbsentWorkerTasks(String worker) {
        // ...
    }

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        Master master = new Master("localhost:2181");
        master.startZK();
        master.runForMaster();
        master.getWorkers();
        master.bootstrap();
        master.masterExists();
        master.isLeader = true;
        if(master.isLeader){
            System.out.println("I'm the leader");
            Thread.sleep(30000);
        }else{
            System.out.println("Some one else is the leader");
        }
        master.stopZK();
    }
}
