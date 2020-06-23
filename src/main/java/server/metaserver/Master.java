package server.metaserver;

import grpc.data.DeleteResponse;
import grpc.locator.LocateRequest;
import grpc.locator.LocateResponse;
import grpc.locator.LocatorServicesGrpc;
import io.grpc.stub.StreamObserver;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import server.Server;
import util.DataProvider;
import util.StringKey;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class Master extends Server {
    private static final Logger logger = Logger.getLogger(Master.class.getName());
    private ConcurrentHashMap<String, String> workers = new ConcurrentHashMap<>();

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
        workers = newWorkers;
    }
    
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        final Master server = new Master("127.0.0.1", 23333);
        server.connectZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
        logger.info("ZooKeeper connected");
        server.bootstrap();

        server.start(23333, new LocatorServicesImpl(server.workers));
        logger.info("RPC Server started, listening on " + 23333);
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
        private final ConcurrentHashMap<String, String> workers;

        public LocatorServicesImpl(ConcurrentHashMap<String, String> workers) {
            this.workers = workers;
        }

        @Override
        public void locate(LocateRequest request, StreamObserver<LocateResponse> responseObserver) {
            LocateResponse response;
//            if (workers.contains(request.getKey())){
            if(true){
                response = LocateResponse.newBuilder().setStatus(1).setAddress(workers.values().iterator().next()).build();
            }else{
                response = LocateResponse.newBuilder().setStatus(0).build();
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
