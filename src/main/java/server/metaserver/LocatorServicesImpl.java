package server.metaserver;

import grpc.locator.LocateRequest;
import grpc.locator.LocateResponse;
import grpc.locator.LocatorServicesGrpc;
import io.grpc.stub.StreamObserver;
import util.Partition;
import util.StringKey;

class LocatorServicesImpl extends LocatorServicesGrpc.LocatorServicesImplBase {
    private final Partition taskPartition;

    public LocatorServicesImpl(Partition taskPartition) {
        this.taskPartition = taskPartition;
    }

    @Override
    public void locate(LocateRequest request, StreamObserver<LocateResponse> responseObserver) {
        LocateResponse response;
        String address = taskPartition.getPartition(new StringKey(request.getKey())); // TODO
        if (address != null){
            response = LocateResponse.newBuilder().setStatus(1).setAddress(address).build();
        }else{
            response = LocateResponse.newBuilder().setStatus(0).build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
