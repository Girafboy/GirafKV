package client;

import grpc.data.*;
import grpc.locator.LocateRequest;
import grpc.locator.LocateResponse;
import grpc.locator.LocatorServicesGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import util.*;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements KVOps {
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private final LocatorServicesGrpc.LocatorServicesBlockingStub locatorServicesBlockingStub;

    public Client(Channel channel) {
        locatorServicesBlockingStub = LocatorServicesGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) throws Exception {
        String target = "127.0.0.1:23333";
        if (args.length > 0) {
            if ("--help".equals(args[0])) {
                System.err.println("Usage: [name [target]]");
                System.err.println("");
                System.err.println("  target  The server to connect to. Defaults to " + target);
                System.exit(1);
            }
        }
        if (args.length > 1) {
            target = args[1];
        }

        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
        try {
            Client client = new Client(channel);
            client.put(new StringKey("key1"), new StringValue("value1"));
            client.get(new StringKey("key1"));
            client.delete(new StringKey("key1"));
            client.get(new StringKey("key1"));
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    public String locate(Key key) {
        logger.info("Locate " + key);
        LocateRequest request = LocateRequest.newBuilder().setKey(key.toString()).build();
        LocateResponse response;
        try {
            response = locatorServicesBlockingStub.locate(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return null;
        }

        if (response.getStatus() != 1) {
            logger.info("Not Found: " + response.getStatus());
            return null;
        }
        logger.info("Locate: " + key + " -> " + response.getAddress());
        return response.getAddress();
    }

    public Value get(Key key) {
        DataServicesGrpc.DataServicesBlockingStub dataServicesBlockingStub =
                DataServicesGrpc.newBlockingStub(
                        ManagedChannelBuilder.forTarget(locate(key))
                                .usePlaintext()
                                .build());

        logger.info("Get " + key);
        GetRequest request = GetRequest.newBuilder().setKey(key.toString()).build();
        GetResponse response;
        try {
            response = dataServicesBlockingStub.get(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return new StringValue("");
        }

        if (response.getStatus() != 1) {
            logger.info("Not Found: " + response.getStatus());
            return new StringValue("");
        }
        logger.info("Get: " + key + " -> " + response.getValue());
        return new StringValue(response.getValue());
    }

    public Boolean put(Key key, Value value) {
        DataServicesGrpc.DataServicesBlockingStub dataServicesBlockingStub =
                DataServicesGrpc.newBlockingStub(
                        ManagedChannelBuilder.forTarget(locate(key))
                                .usePlaintext()
                                .build());

        logger.info("Put " + key + " " + value);
        PutRequest request = PutRequest.newBuilder().setKey(key.toString()).setValue(value.toString()).build();
        PutResponse response;
        try {
            response = dataServicesBlockingStub.put(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return false;
        }

        if (response.getStatus() != 1) {
            logger.info("Put Fail: " + response.getStatus());
            return false;
        }

        logger.info("Put Success!");
        return true;
    }

    public Boolean delete(Key key) {
        DataServicesGrpc.DataServicesBlockingStub dataServicesBlockingStub =
                DataServicesGrpc.newBlockingStub(
                        ManagedChannelBuilder.forTarget(locate(key))
                                .usePlaintext()
                                .build());

        logger.info("Delete " + key);
        DeleteRequest request = DeleteRequest.newBuilder().setKey(key.toString()).build();
        DeleteResponse response;
        try {
            response = dataServicesBlockingStub.delete(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return false;
        }

        if (response.getStatus() != 1) {
            logger.info("Delete Fail: " + response.getStatus());
            return false;
        }

        logger.info("Delete Success!");
        return true;
    }
}
