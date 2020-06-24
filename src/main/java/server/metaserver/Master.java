package server.metaserver;

import grpc.data.DataServicesGrpc;
import grpc.data.DeleteResponse;
import grpc.data.GetRequest;
import grpc.data.GetResponse;
import grpc.locator.LocateRequest;
import grpc.locator.LocateResponse;
import grpc.locator.LocatorServicesGrpc;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import server.Server;
import util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Master extends Server {
    private static final Logger logger = Logger.getLogger(Master.class.getName());
    private ConcurrentHashMap<String, String> workers = new ConcurrentHashMap<>();
    private final Partition taskPartition = new ConsistencyHashPartition();

    public Master(String ip, int port) {
        super(ip, port);
    }

    private void bootstrap() throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists("/workers", false);
        if(stat == null){
            zooKeeper.create("/workers", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        updateWorkers(zooKeeper.getChildren("/workers", true));
    }

    private void updateWorkers(List<String> childrenList) throws KeeperException, InterruptedException {
        ConcurrentHashMap<String, String> newWorkers = new ConcurrentHashMap<>();
        for (String child :
                childrenList) {
            if(workers.contains(child)){
                newWorkers.put(child, workers.get(child));
            } else {
                newWorkers.put(child, new String(zooKeeper.getData("/workers/" + child, false, null)));
            }
        }

        // 重新划分数据
        // 新增的Worker
        HashSet<String> addedWorkers = new HashSet<>(newWorkers.values());
        addedWorkers.removeAll(workers.values());
        for (String worker :
                addedWorkers) {
            taskPartition.addPartition(worker);
        }
        // 删除的Worker
        HashSet<String> removedWorkers = new HashSet<>(workers.values());
        removedWorkers.removeAll(newWorkers.values());
        for (String worker :
                addedWorkers) {
            taskPartition.removePartition(worker);
        }

        workers = newWorkers;
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        final Master server = new Master("127.0.0.1", 23333);
        server.connectZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
        logger.info("ZooKeeper connected");

        server.start(new LocatorServicesImpl(server.taskPartition));
        logger.info("RPC Server started, listening on " + 23333);

        server.bootstrap();
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
            if ("/workers".equals(watchedEvent.getPath())
                    && watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                try {
                    updateWorkers(zooKeeper.getChildren("/workers", true));
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class LocatorServicesImpl extends LocatorServicesGrpc.LocatorServicesImplBase {
        private final Partition taskPartition;

        public LocatorServicesImpl(Partition taskPartition) {
            this.taskPartition = taskPartition;
        }

        @Override
        public void locate(LocateRequest request, StreamObserver<LocateResponse> responseObserver) {
            LocateResponse response;
            String address = taskPartition.getPartition(new StringKey(request.getKey()));
            if (address != null){
                response = LocateResponse.newBuilder().setStatus(1).setAddress(address).build();
            }else{
                response = LocateResponse.newBuilder().setStatus(0).build();
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
