package server.dataserver;

import example.grpc.HelloWorldServer;
import grpc.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import sun.awt.SunHints;
import util.DataProvider;
import util.StringKey;
import util.StringValue;
import util.Value;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Primary {
    private static final Logger logger = Logger.getLogger(HelloWorldServer.class.getName());
    private Server server;

    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new DataServicesImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                    Primary.this.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final Primary server = new Primary();
        server.start();
        server.blockUntilShutdown();
    }

    static class DataServicesImpl extends DataServicesGrpc.DataServicesImplBase {
        private final DataProvider dataProvider = new DataProvider();

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
