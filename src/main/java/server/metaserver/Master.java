package server.metaserver;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import server.Server;
import util.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Master extends Server {
    private static final Logger logger = Logger.getLogger(Master.class.getName());
    private final GroupManager groupManager = new GroupManager();
    private final HashSet<String> workers = new HashSet<>();
    private final Partition taskPartition = new ConsistencyHashPartition();

    public Master(String ip, int port) {
        super(ip, port);
    }

    private void register() {
        zooKeeper.checkAndCreate("/secondary");
        for (String child :
                zooKeeper.getChildren("/secondary", false)) {
            zooKeeper.delete("/secondary/" + child);
        }

        updateWorkers();
        zooKeeper.create("/master", getAddress(), CreateMode.EPHEMERAL);
    }

    private void updateWorkers() {
        groupManager.clear();
        for (String groupId :
                zooKeeper.getChildren("/secondary", true)) {
            for (Map.Entry<String, String> entry :
                    zooKeeper.getChildrenValues("/secondary/" + groupId, true).entrySet()) {
                groupManager.addWorker(entry.getKey(), entry.getValue(), Integer.parseInt(groupId));
                if (entry.getKey().contains("primary")) {
                    groupManager.upgradeAsPrimary(Integer.parseInt(groupId), entry.getKey());
                    if (!taskPartition.containGroupId(Integer.parseInt(groupId))) {
                        // 新增的Group
                        taskPartition.addPartition(
                                zooKeeper.getData("/secondary/" + groupId + "/primary" + groupId, false, null),
                                Integer.parseInt(groupId)
                        );
                    } else {
                        // 修改Group的Partition
                        taskPartition.changePartition(entry.getValue(), Integer.parseInt(groupId));
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        if (args.length != 2) {
            System.err.println("Usage: ip port");
            System.exit(1);
        }
        final String ip = args[0];
        final int port = Integer.parseInt(args[1]);

        final Master server = new Master(ip, port);
        server.connectZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
        logger.info("ZooKeeper connected");

        server.start(new LocatorServicesImpl(server.taskPartition, server.groupManager));
        logger.info("RPC Server started, listening on " + server.getAddress());

        server.register();
        server.blockUntilShutdown();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
        if (watchedEvent.getType() == Event.EventType.None) {
            // We are are being told that the state of the
            // connection has changed
            switch (watchedEvent.getState()) {
                case SyncConnected:
                    // In this particular example we don't need to do anything
                    // here - watches are automatically re-registered with
                    // server and any watches triggered while the client was
                    // disconnected will be delivered (in order of course)
                    break;
                case Expired:
                    // It's all over
                    // TODO
                    break;
            }
        } else {
            if (watchedEvent.getPath() != null && watchedEvent.getPath().contains("/secondary")) {
                updateWorkers();
            }
        }
    }
}
