package util;

import grpc.data.DataServicesGrpc;
import grpc.data.GetRequest;
import grpc.data.GetResponse;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.AbstractBlockingStub;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class RpcCall {
    public static Object oneTimeRpcCall(String address, Class servicesGrpc, RpcCallInterface method) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(address)
                .usePlaintext()
                .build();

        try {
            return method.doMethod(servicesGrpc.getMethod("newBlockingStub", Channel.class).invoke(null, channel));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        } finally {
            channel.shutdownNow();
        }
        return null;
    }
}
