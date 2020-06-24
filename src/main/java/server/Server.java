package server;

import io.grpc.BindableService;
import io.grpc.ServerBuilder;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public abstract class Server implements Watcher {
    private io.grpc.Server server;
    protected ZooKeeper zooKeeper;
    private String ip;
    private int port;

    public Server(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    protected void start(BindableService service) throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(service)
                .build()
                .start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                    Server.this.stop();
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

    protected void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    protected void connectZooKeeper(String address) throws IOException {
        zooKeeper = new ZooKeeper(address, 15000, this);
    }

    public String getAddress() {
        return ip + ":" + port;
    }
}
