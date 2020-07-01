package server.dataserver;

import grpc.data.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import server.Server;
import util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class DataNode extends Server {
    private static final Logger logger = Logger.getLogger(DataNode.class.getName());
    private final DataProvider dataProvider = new DataProvider();
    private final AddressManager addressManager = new AddressManager();
    private Boolean isPrimary = false;
    private Integer groupId;
    private String nodeName; //TODO

    public DataNode(String ip, int port, int groupId) {
        super(ip, port);
        this.groupId = groupId;
    }

    private void syncData() {
        logger.info("Sync Data From " + addressManager.getPrimaryAddress() + " To " + getAddress());
        SyncResponse syncResponse = (SyncResponse) RpcCall.oneTimeRpcCall(
                addressManager.getPrimaryAddress(),
                DataServicesGrpc.class,
                (RpcCallInterface<DataServicesGrpc.DataServicesBlockingStub, SyncResponse>) stub -> {
                    SyncRequest syncRequest = SyncRequest.newBuilder().build();
                    return stub.sync(syncRequest);
                }
        );
        for (Slot slot :
                syncResponse.getSlotsList()) {
            dataProvider.addSlot(slot.getSlotId());
            for (Entry entry:
                    slot.getEntriesList()) {
                dataProvider.put(new StringKey(entry.getKey()), new StringValue(entry.getValue()));
            }
        }
    }

    private void runForPrimary() {
        // 竞选
        zooKeeper.create("/primary/" + groupId, getAddress(), CreateMode.EPHEMERAL, false);

        // 读取竞选结果
        addressManager.setPrimaryAddress(zooKeeper.getData("/primary/" + groupId, true, null));
        if (addressManager.getPrimaryAddress().equals(getAddress())){
            isPrimary = true;
            if (nodeName != null){
                zooKeeper.delete("/secondary/" + groupId + "/" + nodeName);
                nodeName = zooKeeper.create(
                        "/secondary/" + groupId + "/primary" + groupId,
                        getAddress(),
                        CreateMode.EPHEMERAL
                ).substring(("/secondary/" + groupId + "/").length());
            }
            logger.info("Election Leader success!");
        } else {
            isPrimary = false;
            logger.info("Election Leader fail!");
            syncData();
        }

        addressManager.setSecondaryAddresses(new ArrayList<>(zooKeeper.getChildrenValues("/secondary/" + groupId, true).values()));
    }

    private void register() {
        if(!zooKeeper.exists("/master", true)){
            synchronized (this){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (isPrimary){
            nodeName = zooKeeper.create(
                    "/secondary/" + groupId + "/primary" + groupId,
                    getAddress(),
                    CreateMode.EPHEMERAL
            ).substring(("/secondary/" + groupId + "/").length());
        } else {
            nodeName = zooKeeper.create(
                    "/secondary/" + groupId + "/secondary",
                    getAddress(),
                    CreateMode.EPHEMERAL_SEQUENTIAL
            ).substring(("/secondary/" + groupId + "/").length());
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        if (args.length != 2) {
            System.err.println("Usage: ip port");
            System.exit(1);
        }
        final String ip = args[0];
        final int port = Integer.parseInt(args[1]);

        final DataNode server = new DataNode(ip, 50053, 1);
        server.connectZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
        logger.info("ZooKeeper connected");

        // 0. 准备目录
        server.zooKeeper.checkAndCreate("/primary");
        server.zooKeeper.checkAndCreate("/secondary");
        server.zooKeeper.checkAndCreate("/secondary/" + server.groupId);

        server.start(new PrimarySecondaryServices(server.dataProvider, server.addressManager));
        logger.info("Primary RPC Server started, listening on " + server.getAddress());

        // 1. 竞选Primary
        server.runForPrimary();
        // 2. 报告自己服务可用
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
            if ("/master".equals(watchedEvent.getPath())) {
                synchronized (this){
                    this.notifyAll();
                }
            } else if (("/primary/" + groupId).equals(watchedEvent.getPath()) && watchedEvent.getType().equals(Event.EventType.NodeDeleted)) {
                runForPrimary();
            } else if (("/secondary/" + groupId).equals(watchedEvent.getPath())) {
                addressManager.setSecondaryAddresses(new ArrayList<>(zooKeeper.getChildrenValues("/secondary/" + groupId, true).values()));
            }
        }
    }

    class AddressManager {
        private String primaryAddress;
        private ArrayList<String> secondaryAddresses;

        public String getPrimaryAddress() {
            return primaryAddress;
        }

        public void setPrimaryAddress(String primaryAddress) {
            this.primaryAddress = primaryAddress;
        }

        public ArrayList<String> getSecondaryAddresses() {
            return secondaryAddresses;
        }

        public void setSecondaryAddresses(ArrayList<String> secondaryAddresses) {
            this.secondaryAddresses = secondaryAddresses;
        }
    }
}
