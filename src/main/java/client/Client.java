package client;

import grpc.*;
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

    private final DataServicesGrpc.DataServicesBlockingStub blockingStub;

    public Client(Channel channel) {
        blockingStub = DataServicesGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) throws Exception {
        String target = "localhost:50051";
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

    public Value get(Key key) {
        logger.info("Get " + key);
        GetRequest request = GetRequest.newBuilder().setKey(key.toString()).build();
        GetResponse response;
        try {
            response = blockingStub.get(request);
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
        logger.info("Put " + key + " " + value);
        PutRequest request = PutRequest.newBuilder().setKey(key.toString()).setValue(value.toString()).build();
        PutResponse response;
        try {
            response = blockingStub.put(request);
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
        logger.info("Delete " + key);
        DeleteRequest request = DeleteRequest.newBuilder().setKey(key.toString()).build();
        DeleteResponse response;
        try {
            response = blockingStub.delete(request);
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
