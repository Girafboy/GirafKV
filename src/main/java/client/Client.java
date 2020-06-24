package client;

import grpc.data.*;
import grpc.locator.LocateRequest;
import grpc.locator.LocateResponse;
import grpc.locator.LocatorServicesGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.AbstractBlockingStub;
import util.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Scanner;
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
            System.out.println("Testing...");
            client.put(new StringKey("key1"), new StringValue("value1"));
            client.get(new StringKey("key1"));
            client.delete(new StringKey("key1"));
            client.get(new StringKey("key1"));
            System.out.println("Test Finish! Start Terminal!");

            Scanner scanner = new Scanner(System.in);
            final String help = "Usage: [get|delete] key\n" + "       put key value";
            while (true) {
                System.out.print("Girafboy KV > ");
                String command = scanner.nextLine();
                String[] strings = command.split("\\s+");
                if (strings.length < 2){
                    System.out.println(help);
                    continue;
                }

                switch (strings[0]) {
                    case "put":
                        if (strings.length != 3) {
                            System.out.println(help);
                            continue;
                        }
                        client.put(new StringKey(strings[1]), new StringValue(strings[2]));
                        break;
                    case "get":
                        if (strings.length != 2) {
                            System.out.println(help);
                            continue;
                        }
                        client.get(new StringKey(strings[1]));
                        break;
                    case "delete":
                        if (strings.length != 2) {
                            System.out.println(help);
                            continue;
                        }
                        client.delete(new StringKey(strings[1]));
                        break;
                    default:
                        System.out.println(help);
                        break;
                }
            }
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
        GetResponse response = (GetResponse) RpcCall.oneTimeRpcCall(
                locate(key),
                DataServicesGrpc.class,
                (RpcCallInterface<DataServicesGrpc.DataServicesBlockingStub, GetResponse>) stub -> {
                    logger.info("Get " + key);
                    GetRequest request = GetRequest.newBuilder().setKey(key.toString()).build();
                    return stub.get(request);
                }
        );

        assert response != null;
        if (response.getStatus() != 1) {
            logger.info("Not Found: " + response.getStatus());
            return new StringValue("");
        }
        logger.info("Get: " + key + " -> " + response.getValue());
        return new StringValue(response.getValue());
    }

    public Boolean put(Key key, Value value) {
        PutResponse response = (PutResponse) RpcCall.oneTimeRpcCall(
                locate(key),
                DataServicesGrpc.class,
                (RpcCallInterface<DataServicesGrpc.DataServicesBlockingStub, PutResponse>) stub -> {
                    logger.info("Put " + key + " " + value);
                    PutRequest request = PutRequest.newBuilder().setKey(key.toString()).setValue(value.toString()).build();
                    PutResponse response1;
                    return stub.put(request);
                }
        );

        assert response != null;
        if (response.getStatus() != 1) {
            logger.info("Put Fail: " + response.getStatus());
            return false;
        }
        logger.info("Put Success!");
        return true;
    }

    public Boolean delete(Key key) {
        DeleteResponse response = (DeleteResponse) RpcCall.oneTimeRpcCall(
                locate(key),
                DataServicesGrpc.class,
                (RpcCallInterface<DataServicesGrpc.DataServicesBlockingStub, DeleteResponse>) stub -> {
                    logger.info("Delete " + key);
                    DeleteRequest request = DeleteRequest.newBuilder().setKey(key.toString()).build();
                    return stub.delete(request);
                }
        );

        assert response != null;
        if (response.getStatus() != 1) {
            logger.info("Delete Fail: " + response.getStatus());
            return false;
        }
        logger.info("Delete Success!");
        return true;
    }
}
