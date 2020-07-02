package server.metaserver;

import grpc.locator.LocateRequest;
import grpc.locator.LocateResponse;
import grpc.locator.LocatorServicesGrpc;
import io.grpc.stub.StreamObserver;
import partition.Partition;
import util.StringKey;

class LocatorServicesImpl extends LocatorServicesGrpc.LocatorServicesImplBase {
    private final Partition taskPartition;
    private final GroupManager groupManager;

    public LocatorServicesImpl(Partition taskPartition, GroupManager groupManager) {
        this.taskPartition = taskPartition;
        this.groupManager = groupManager;
    }

    @Override
    public void locate(LocateRequest request, StreamObserver<LocateResponse> responseObserver) {
        LocateResponse response;
        Integer groupId = taskPartition.getPartition(new StringKey(request.getKey()));
        String address;
        if (request.getIsWrite() == 1) {
            address = groupManager.getWriteAddress(groupId);
        } else {
            address = groupManager.getReadAddress(groupId);
        }
        if (address != null){
            response = LocateResponse.newBuilder().setStatus(1).setAddress(address).build();
        }else{
            response = LocateResponse.newBuilder().setStatus(0).build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
