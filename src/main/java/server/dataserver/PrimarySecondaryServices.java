package server.dataserver;

import data.DataProvider;
import grpc.data.*;
import io.grpc.stub.StreamObserver;
import logger.LogReaderWriter;
import util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PrimarySecondaryServices extends DataServicesImpl {
    private DataNode.AddressManager addressManager;
    private final Integer groupId;
    private final Set<Integer> syncSeqLog = Collections.newSetFromMap(new ConcurrentHashMap());

    public PrimarySecondaryServices(DataProvider dataProvider, DataNode.AddressManager addressManager, Integer groupId) {
        super(dataProvider);
        this.addressManager = addressManager;
        this.groupId = groupId;
    }

    @Override
    public void put(PutRequest request, StreamObserver<PutResponse> responseObserver) {
        // 获得原子序
        Integer seq = dataProvider.getNextSyncSeq();

        // Phase 1
        ArrayList<String> alreadyAddress = new ArrayList<>();
        for (String address :
                addressManager.getSecondaryAddresses()) {
            PutResponse putResponse = (PutResponse) RpcCall.oneTimeRpcCall(
                    address,
                    DataServicesGrpc.class,
                    (RpcCallInterface<DataServicesGrpc.DataServicesBlockingStub, PutResponse>) stub -> stub.tryPut(
                            TryPutRequest.newBuilder().setKey(request.getKey()).setValue(request.getValue()).setSyncSeq(seq).build()
                    )
            );
            assert putResponse != null;
            if (putResponse.getStatus() == 0) {
                for (String addr :
                        alreadyAddress) {
                    SyncSeq syncSeq = (SyncSeq) RpcCall.oneTimeRpcCall(
                            addr,
                            DataServicesGrpc.class,
                            (RpcCallInterface<DataServicesGrpc.DataServicesBlockingStub, SyncSeq>) stub -> stub.rollBack(
                                    SyncSeq.newBuilder().setSyncSeq(seq).build()
                            )
                    );
                }
                return;
            }
            alreadyAddress.add(address);
        }

        // 确认完成，记录Log
        super.put(request, responseObserver);
        syncSeqLog.add(seq);

        // Phase 2
        boolean successCommit = true;
        for (String address :
                addressManager.getSecondaryAddresses()) {
            SyncSeq syncSeq = (SyncSeq) RpcCall.oneTimeRpcCall(
                    address,
                    DataServicesGrpc.class,
                    (RpcCallInterface<DataServicesGrpc.DataServicesBlockingStub, SyncSeq>) stub -> stub.commit(
                            SyncSeq.newBuilder().setSyncSeq(seq).build()
                    )
            );
            if (syncSeq.getSyncSeq() != seq) {
                successCommit = false;
            }
        }

        // 确认完成，删除Log
        if (successCommit) {
            syncSeqLog.remove(seq);
        }

        // Log持久化
        LogReaderWriter.write("tmp/group" + groupId, syncSeqLog);
    }

    @Override
    public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
        // 获得原子序
        Integer seq = dataProvider.getNextSyncSeq();

        // Phase 1
        for (String address :
                addressManager.getSecondaryAddresses()) {
            DeleteResponse deleteResponse = (DeleteResponse) RpcCall.oneTimeRpcCall(
                    address,
                    DataServicesGrpc.class,
                    (RpcCallInterface<DataServicesGrpc.DataServicesBlockingStub, DeleteResponse>) stub -> stub.tryDelete(
                            TryDeleteRequest.newBuilder().setKey(request.getKey()).setSyncSeq(seq).build()
                    )
            );
            assert deleteResponse != null;
            if (deleteResponse.getStatus() == 0) {
                return;
            }
        }

        // 确认完成，记录Log
        super.delete(request, responseObserver);
        syncSeqLog.add(seq);

        // Phase 2
        boolean successCommit = true;
        for (String address :
                addressManager.getSecondaryAddresses()) {
            SyncSeq syncSeq = (SyncSeq) RpcCall.oneTimeRpcCall(
                    address,
                    DataServicesGrpc.class,
                    (RpcCallInterface<DataServicesGrpc.DataServicesBlockingStub, SyncSeq>) stub -> stub.commit(
                            SyncSeq.newBuilder().setSyncSeq(seq).build()
                    )
            );
            if (syncSeq.getSyncSeq() != seq) {
                successCommit = false;
            }
        }

        // 确认完成，删除Log
        if (successCommit) {
            syncSeqLog.remove(seq);
        }

        // Log持久化
        LogReaderWriter.write("tmp/group" + groupId, syncSeqLog);
    }

    @Override
    public void sync(SyncRequest request, StreamObserver<SyncResponse> responseObserver) {
        ArrayList<Slot> slots = new ArrayList<>();
        for (Integer slotId :
                dataProvider.getSlots()) {
            Slot.Builder builder = Slot.newBuilder().setSlotId(slotId);
            for (Map.Entry<Key, Value> entry :
                    dataProvider.getSlotEntries(slotId).entrySet()) {
                builder.addEntries(Entry.newBuilder().setKey(entry.getKey().toString()).setValue(entry.getValue().toString()).build());
            }
            slots.add(builder.build());
        }
        SyncResponse response = SyncResponse.newBuilder().addAllSlots(slots).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void tryPut(TryPutRequest request, StreamObserver<PutResponse> responseObserver) {
        PutResponse response;
        dataProvider.tryPut(new StringKey(request.getKey()), new StringValue(request.getValue()), request.getSyncSeq());
        response = PutResponse.newBuilder().setStatus(1).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void tryDelete(TryDeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
        DeleteResponse response;
        dataProvider.tryDelete(new StringKey(request.getKey()),request.getSyncSeq());
        response = DeleteResponse.newBuilder().setStatus(1).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void commit(SyncSeq request, StreamObserver<SyncSeq> responseObserver) {
        SyncSeq response;
        for (Integer oldSyncSeq :
                dataProvider.allBufferSyncSeq(request.getSyncSeq())) {
            if (oldSyncSeq < request.getSyncSeq()) {
                SyncSeq syncSeq = (SyncSeq) RpcCall.oneTimeRpcCall(
                        addressManager.getPrimaryAddress(),
                        DataServicesGrpc.class,
                        (RpcCallInterface<DataServicesGrpc.DataServicesBlockingStub, SyncSeq>) stub -> stub.checkLog(
                                SyncSeq.newBuilder().setSyncSeq(oldSyncSeq).build()
                        )
                );
                if (syncSeq.getSyncSeq() == oldSyncSeq) {
                    dataProvider.commit(request.getSyncSeq());
                } else {
                    dataProvider.rollback(request.getSyncSeq());
                }
            }
        }

        dataProvider.commit(request.getSyncSeq());
        response = SyncSeq.newBuilder().setSyncSeq(request.getSyncSeq()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void rollBack(SyncSeq request, StreamObserver<SyncSeq> responseObserver) {
        SyncSeq response;
        dataProvider.rollback(request.getSyncSeq());
        response = SyncSeq.newBuilder().setSyncSeq(request.getSyncSeq()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void checkLog(SyncSeq request, StreamObserver<SyncSeq> responseObserver) {
        SyncSeq response;
        if (syncSeqLog.contains(request.getSyncSeq())){
            response = SyncSeq.newBuilder().setSyncSeq(request.getSyncSeq()).build();
        } else {
            response = SyncSeq.newBuilder().setSyncSeq(-1).build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
