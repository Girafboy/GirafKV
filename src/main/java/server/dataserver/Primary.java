package server.dataserver;

import example.grpc.HelloWorldServer;
import grpc.data.*;
import grpc.locator.LocateResponse;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import server.Server;
import sun.reflect.generics.reflectiveObjects.LazyReflectiveObjectGenerator;
import util.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Primary extends Server {
    private static final Logger logger = Logger.getLogger(Primary.class.getName());
    private final DataProvider dataProvider = new DataProvider();

    public Primary(String ip, int port) {
        super(ip, port);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        if (args.length != 2) {
            System.err.println("Usage: ip port");
            System.exit(1);
        }
        final String ip = args[0];
        final int port = Integer.parseInt(args[1]);

        final Primary server = new Primary(ip, 50055);
        server.connectZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
        logger.info("ZooKeeper connected");

        server.start(new DataServicesImpl(server.dataProvider));
        logger.info("RPC Server started, listening on " + server.getAddress());

        Stat stat = server.zooKeeper.exists("/workers", true);
        if(stat == null){
            synchronized (server){
                server.wait();
            }
        }
        server.zooKeeper.create("/workers/worker", server.getAddress().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
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

        @Override
        public void migrateFrom(MigrateRequest request, StreamObserver<MigrateResponse> responseObserver) {
            MigrateResponse response;

            for (MigrateSlice slice :
                    request.getMigrateSlicesList()) {
                // 需要从其他地方拿，否则直接自己分配
                if(!slice.getAddress().isEmpty()) {
                    TransferDataFromResponse transferDataFromResponse = (TransferDataFromResponse) RpcCall.oneTimeRpcCall(
                            slice.getAddress(),
                            DataServicesGrpc.class,
                            (RpcCallInterface<DataServicesGrpc.DataServicesBlockingStub, TransferDataFromResponse>) stub -> {
                                TransferDataFromRequest transferDataFromRequest = TransferDataFromRequest.newBuilder().addAllSlotId(
                                        slice.getSlotIdList()
                                ).build();
                                return stub.transferDataFrom(transferDataFromRequest);
                            }
                    );

                    assert transferDataFromResponse != null;
                    for (Entry entry :
                            transferDataFromResponse.getEntriesList()) {
                        dataProvider.put(new StringKey(entry.getKey()), new StringValue(entry.getValue()));
                    }
                }
                dataProvider.addSlot(slice.getSlotIdList());
            }
            response = MigrateResponse.newBuilder().setStatus(1).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void migrateTo(MigrateRequest request, StreamObserver<MigrateResponse> responseObserver) {
            super.migrateTo(request, responseObserver);
            // TODO
        }

        @Override
        public void transferDataFrom(TransferDataFromRequest request, StreamObserver<TransferDataFromResponse> responseObserver) {
            TransferDataFromResponse.Builder builder = TransferDataFromResponse.newBuilder();
            for (int slotId :
                    request.getSlotIdList()) {
                for (Map.Entry<Key, Value> entry:
                        dataProvider.getSlotEntries(slotId).entrySet()){
                    builder.addEntries(Entry.newBuilder().setKey(entry.getKey().toString()).setValue(entry.getValue().toString()).build());
                }
            }
            TransferDataFromResponse response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void transferDataTo(TransferDataToRequest request, StreamObserver<TransferDataToResponse> responseObserver) {
            super.transferDataTo(request, responseObserver);
            // TODO
        }
    }
}
