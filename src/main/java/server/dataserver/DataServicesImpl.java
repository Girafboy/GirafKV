package server.dataserver;

import grpc.data.*;
import io.grpc.stub.StreamObserver;
import util.*;

import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class DataServicesImpl extends DataServicesGrpc.DataServicesImplBase {
    protected final DataProvider dataProvider;
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public DataServicesImpl(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public void put(PutRequest request, StreamObserver<PutResponse> responseObserver) {
        readWriteLock.writeLock().lock();
        PutResponse response;
        if (dataProvider.put(new StringKey(request.getKey()), new StringValue(request.getValue()))){
            response = PutResponse.newBuilder().setStatus(1).build();
        }else{
            response = PutResponse.newBuilder().setStatus(0).build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        readWriteLock.writeLock().unlock();
    }

    @Override
    public void get(GetRequest request, StreamObserver<GetResponse> responseObserver) {
        readWriteLock.readLock().lock();
        Value value = dataProvider.get(new StringKey(request.getKey()));
        GetResponse response;
        if (value != null){
            response = GetResponse.newBuilder().setStatus(1).setValue(value.toString()).build();
        } else {
            response = GetResponse.newBuilder().setStatus(0).setValue("").build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        readWriteLock.readLock().unlock();
    }

    @Override
    public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
        readWriteLock.writeLock().lock();
        DeleteResponse response;
        if (dataProvider.delete(new StringKey(request.getKey()))){
            response = DeleteResponse.newBuilder().setStatus(1).build();
        }else{
            response = DeleteResponse.newBuilder().setStatus(0).build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        readWriteLock.writeLock().unlock();
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
