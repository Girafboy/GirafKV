package server.dataserver;

import grpc.data.*;
import io.grpc.stub.StreamObserver;
import util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrimarySecondaryServices extends DataServicesImpl {
    private DataNode.AddressManager addressManager;

    public PrimarySecondaryServices(DataProvider dataProvider, DataNode.AddressManager addressManager) {
        super(dataProvider);
        this.addressManager = addressManager;
    }

    @Override
    public void put(PutRequest request, StreamObserver<PutResponse> responseObserver) {
        Integer seq;
        synchronized (this) {
            super.put(request, responseObserver);
            seq = dataProvider.getNextSyncSeq();
        }
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
                return;
            }
        }
        for (String address :
                addressManager.getSecondaryAddresses()) {
            SyncSeq syncSeq = (SyncSeq) RpcCall.oneTimeRpcCall(
                    address,
                    DataServicesGrpc.class,
                    (RpcCallInterface<DataServicesGrpc.DataServicesBlockingStub, SyncSeq>) stub -> stub.commit(
                            SyncSeq.newBuilder().setSyncSeq(seq).build()
                    )
            );
        }
    }

    @Override
    public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
        Integer seq;
        synchronized (this) {
            super.delete(request, responseObserver);
            seq = dataProvider.getNextSyncSeq();
        }
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
        for (String address :
                addressManager.getSecondaryAddresses()) {
            SyncSeq syncSeq = (SyncSeq) RpcCall.oneTimeRpcCall(
                    address,
                    DataServicesGrpc.class,
                    (RpcCallInterface<DataServicesGrpc.DataServicesBlockingStub, SyncSeq>) stub -> stub.commit(
                            SyncSeq.newBuilder().setSyncSeq(seq).build()
                    )
            );
        }
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
        dataProvider.commit(request.getSyncSeq());
        response = SyncSeq.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void rollBack(SyncSeq request, StreamObserver<SyncSeq> responseObserver) {
        super.rollBack(request, responseObserver);
        // TODO
    }
}
