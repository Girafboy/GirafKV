package server.dataserver;

import example.grpc.HelloWorldServer;
import grpc.data.*;
import io.grpc.stub.StreamObserver;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import server.Server;
import util.DataProvider;
import util.StringKey;
import util.StringValue;
import util.Value;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Primary extends Server {
    private static final Logger logger = Logger.getLogger(Primary.class.getName());
    private final DataProvider dataProvider = new DataProvider();

    public Primary(String ip, int port) {
        super(ip, port);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        final Primary server = new Primary("127.0.0.1", 50051);
        server.connectZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
        logger.info("ZooKeeper connected");
        Stat stat = server.zooKeeper.exists("/workers", true);
        if(stat == null){
            synchronized (server){
                server.wait();
            }
        }
        server.zooKeeper.create("/workers/worker", server.getAddress().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        server.start(50051, new DataServicesImpl(server.dataProvider));
        logger.info("RPC Server started, listening on " + 50051);
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
            if ("/workers".equals(watchedEvent.getPath())) {
                synchronized (this){
                    this.notifyAll();
                }
            }
        }
    }

    static class DataServicesImpl extends DataServicesGrpc.DataServicesImplBase {
        private final DataProvider dataProvider;

        public DataServicesImpl(DataProvider dataProvider) {
            this.dataProvider = dataProvider;
        }

        @Override
        public void put(PutRequest request, StreamObserver<PutResponse> responseObserver) {
            PutResponse response;
            if (dataProvider.put(new StringKey(request.getKey()), new StringValue(request.getValue()))){
                response = PutResponse.newBuilder().setStatus(1).build();
            }else{
                response = PutResponse.newBuilder().setStatus(0).build();
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void get(GetRequest request, StreamObserver<GetResponse> responseObserver) {
            Value value = dataProvider.get(new StringKey(request.getKey()));
            GetResponse response;
            if (value != null){
                response = GetResponse.newBuilder().setStatus(1).setValue(value.toString()).build();
            } else {
                response = GetResponse.newBuilder().setStatus(0).setValue("").build();
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
            DeleteResponse response;
            if (dataProvider.delete(new StringKey(request.getKey()))){
                response = DeleteResponse.newBuilder().setStatus(1).build();
            }else{
                response = DeleteResponse.newBuilder().setStatus(0).build();
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
