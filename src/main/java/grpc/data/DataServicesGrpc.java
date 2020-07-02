package grpc.data;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.30.0)",
    comments = "Source: dataservices.proto")
public final class DataServicesGrpc {

  private DataServicesGrpc() {}

  public static final String SERVICE_NAME = "dataservices.DataServices";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.data.PutRequest,
      grpc.data.PutResponse> getPutMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Put",
      requestType = grpc.data.PutRequest.class,
      responseType = grpc.data.PutResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.data.PutRequest,
      grpc.data.PutResponse> getPutMethod() {
    io.grpc.MethodDescriptor<grpc.data.PutRequest, grpc.data.PutResponse> getPutMethod;
    if ((getPutMethod = DataServicesGrpc.getPutMethod) == null) {
      synchronized (DataServicesGrpc.class) {
        if ((getPutMethod = DataServicesGrpc.getPutMethod) == null) {
          DataServicesGrpc.getPutMethod = getPutMethod =
              io.grpc.MethodDescriptor.<grpc.data.PutRequest, grpc.data.PutResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Put"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.PutRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.PutResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataServicesMethodDescriptorSupplier("Put"))
              .build();
        }
      }
    }
    return getPutMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.data.GetRequest,
      grpc.data.GetResponse> getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Get",
      requestType = grpc.data.GetRequest.class,
      responseType = grpc.data.GetResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.data.GetRequest,
      grpc.data.GetResponse> getGetMethod() {
    io.grpc.MethodDescriptor<grpc.data.GetRequest, grpc.data.GetResponse> getGetMethod;
    if ((getGetMethod = DataServicesGrpc.getGetMethod) == null) {
      synchronized (DataServicesGrpc.class) {
        if ((getGetMethod = DataServicesGrpc.getGetMethod) == null) {
          DataServicesGrpc.getGetMethod = getGetMethod =
              io.grpc.MethodDescriptor.<grpc.data.GetRequest, grpc.data.GetResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Get"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.GetRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.GetResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataServicesMethodDescriptorSupplier("Get"))
              .build();
        }
      }
    }
    return getGetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.data.DeleteRequest,
      grpc.data.DeleteResponse> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Delete",
      requestType = grpc.data.DeleteRequest.class,
      responseType = grpc.data.DeleteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.data.DeleteRequest,
      grpc.data.DeleteResponse> getDeleteMethod() {
    io.grpc.MethodDescriptor<grpc.data.DeleteRequest, grpc.data.DeleteResponse> getDeleteMethod;
    if ((getDeleteMethod = DataServicesGrpc.getDeleteMethod) == null) {
      synchronized (DataServicesGrpc.class) {
        if ((getDeleteMethod = DataServicesGrpc.getDeleteMethod) == null) {
          DataServicesGrpc.getDeleteMethod = getDeleteMethod =
              io.grpc.MethodDescriptor.<grpc.data.DeleteRequest, grpc.data.DeleteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.DeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.DeleteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataServicesMethodDescriptorSupplier("Delete"))
              .build();
        }
      }
    }
    return getDeleteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.data.MigrateRequest,
      grpc.data.MigrateResponse> getMigrateFromMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MigrateFrom",
      requestType = grpc.data.MigrateRequest.class,
      responseType = grpc.data.MigrateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.data.MigrateRequest,
      grpc.data.MigrateResponse> getMigrateFromMethod() {
    io.grpc.MethodDescriptor<grpc.data.MigrateRequest, grpc.data.MigrateResponse> getMigrateFromMethod;
    if ((getMigrateFromMethod = DataServicesGrpc.getMigrateFromMethod) == null) {
      synchronized (DataServicesGrpc.class) {
        if ((getMigrateFromMethod = DataServicesGrpc.getMigrateFromMethod) == null) {
          DataServicesGrpc.getMigrateFromMethod = getMigrateFromMethod =
              io.grpc.MethodDescriptor.<grpc.data.MigrateRequest, grpc.data.MigrateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "MigrateFrom"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.MigrateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.MigrateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataServicesMethodDescriptorSupplier("MigrateFrom"))
              .build();
        }
      }
    }
    return getMigrateFromMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.data.MigrateRequest,
      grpc.data.MigrateResponse> getMigrateToMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MigrateTo",
      requestType = grpc.data.MigrateRequest.class,
      responseType = grpc.data.MigrateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.data.MigrateRequest,
      grpc.data.MigrateResponse> getMigrateToMethod() {
    io.grpc.MethodDescriptor<grpc.data.MigrateRequest, grpc.data.MigrateResponse> getMigrateToMethod;
    if ((getMigrateToMethod = DataServicesGrpc.getMigrateToMethod) == null) {
      synchronized (DataServicesGrpc.class) {
        if ((getMigrateToMethod = DataServicesGrpc.getMigrateToMethod) == null) {
          DataServicesGrpc.getMigrateToMethod = getMigrateToMethod =
              io.grpc.MethodDescriptor.<grpc.data.MigrateRequest, grpc.data.MigrateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "MigrateTo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.MigrateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.MigrateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataServicesMethodDescriptorSupplier("MigrateTo"))
              .build();
        }
      }
    }
    return getMigrateToMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.data.TransferDataFromRequest,
      grpc.data.TransferDataFromResponse> getTransferDataFromMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TransferDataFrom",
      requestType = grpc.data.TransferDataFromRequest.class,
      responseType = grpc.data.TransferDataFromResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.data.TransferDataFromRequest,
      grpc.data.TransferDataFromResponse> getTransferDataFromMethod() {
    io.grpc.MethodDescriptor<grpc.data.TransferDataFromRequest, grpc.data.TransferDataFromResponse> getTransferDataFromMethod;
    if ((getTransferDataFromMethod = DataServicesGrpc.getTransferDataFromMethod) == null) {
      synchronized (DataServicesGrpc.class) {
        if ((getTransferDataFromMethod = DataServicesGrpc.getTransferDataFromMethod) == null) {
          DataServicesGrpc.getTransferDataFromMethod = getTransferDataFromMethod =
              io.grpc.MethodDescriptor.<grpc.data.TransferDataFromRequest, grpc.data.TransferDataFromResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TransferDataFrom"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.TransferDataFromRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.TransferDataFromResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataServicesMethodDescriptorSupplier("TransferDataFrom"))
              .build();
        }
      }
    }
    return getTransferDataFromMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.data.TransferDataToRequest,
      grpc.data.TransferDataToResponse> getTransferDataToMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TransferDataTo",
      requestType = grpc.data.TransferDataToRequest.class,
      responseType = grpc.data.TransferDataToResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.data.TransferDataToRequest,
      grpc.data.TransferDataToResponse> getTransferDataToMethod() {
    io.grpc.MethodDescriptor<grpc.data.TransferDataToRequest, grpc.data.TransferDataToResponse> getTransferDataToMethod;
    if ((getTransferDataToMethod = DataServicesGrpc.getTransferDataToMethod) == null) {
      synchronized (DataServicesGrpc.class) {
        if ((getTransferDataToMethod = DataServicesGrpc.getTransferDataToMethod) == null) {
          DataServicesGrpc.getTransferDataToMethod = getTransferDataToMethod =
              io.grpc.MethodDescriptor.<grpc.data.TransferDataToRequest, grpc.data.TransferDataToResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TransferDataTo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.TransferDataToRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.TransferDataToResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataServicesMethodDescriptorSupplier("TransferDataTo"))
              .build();
        }
      }
    }
    return getTransferDataToMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.data.SyncRequest,
      grpc.data.SyncResponse> getSyncMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Sync",
      requestType = grpc.data.SyncRequest.class,
      responseType = grpc.data.SyncResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.data.SyncRequest,
      grpc.data.SyncResponse> getSyncMethod() {
    io.grpc.MethodDescriptor<grpc.data.SyncRequest, grpc.data.SyncResponse> getSyncMethod;
    if ((getSyncMethod = DataServicesGrpc.getSyncMethod) == null) {
      synchronized (DataServicesGrpc.class) {
        if ((getSyncMethod = DataServicesGrpc.getSyncMethod) == null) {
          DataServicesGrpc.getSyncMethod = getSyncMethod =
              io.grpc.MethodDescriptor.<grpc.data.SyncRequest, grpc.data.SyncResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Sync"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.SyncRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.SyncResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataServicesMethodDescriptorSupplier("Sync"))
              .build();
        }
      }
    }
    return getSyncMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.data.TryPutRequest,
      grpc.data.PutResponse> getTryPutMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TryPut",
      requestType = grpc.data.TryPutRequest.class,
      responseType = grpc.data.PutResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.data.TryPutRequest,
      grpc.data.PutResponse> getTryPutMethod() {
    io.grpc.MethodDescriptor<grpc.data.TryPutRequest, grpc.data.PutResponse> getTryPutMethod;
    if ((getTryPutMethod = DataServicesGrpc.getTryPutMethod) == null) {
      synchronized (DataServicesGrpc.class) {
        if ((getTryPutMethod = DataServicesGrpc.getTryPutMethod) == null) {
          DataServicesGrpc.getTryPutMethod = getTryPutMethod =
              io.grpc.MethodDescriptor.<grpc.data.TryPutRequest, grpc.data.PutResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TryPut"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.TryPutRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.PutResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataServicesMethodDescriptorSupplier("TryPut"))
              .build();
        }
      }
    }
    return getTryPutMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.data.TryDeleteRequest,
      grpc.data.DeleteResponse> getTryDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TryDelete",
      requestType = grpc.data.TryDeleteRequest.class,
      responseType = grpc.data.DeleteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.data.TryDeleteRequest,
      grpc.data.DeleteResponse> getTryDeleteMethod() {
    io.grpc.MethodDescriptor<grpc.data.TryDeleteRequest, grpc.data.DeleteResponse> getTryDeleteMethod;
    if ((getTryDeleteMethod = DataServicesGrpc.getTryDeleteMethod) == null) {
      synchronized (DataServicesGrpc.class) {
        if ((getTryDeleteMethod = DataServicesGrpc.getTryDeleteMethod) == null) {
          DataServicesGrpc.getTryDeleteMethod = getTryDeleteMethod =
              io.grpc.MethodDescriptor.<grpc.data.TryDeleteRequest, grpc.data.DeleteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TryDelete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.TryDeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.DeleteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataServicesMethodDescriptorSupplier("TryDelete"))
              .build();
        }
      }
    }
    return getTryDeleteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.data.SyncSeq,
      grpc.data.SyncSeq> getCommitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Commit",
      requestType = grpc.data.SyncSeq.class,
      responseType = grpc.data.SyncSeq.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.data.SyncSeq,
      grpc.data.SyncSeq> getCommitMethod() {
    io.grpc.MethodDescriptor<grpc.data.SyncSeq, grpc.data.SyncSeq> getCommitMethod;
    if ((getCommitMethod = DataServicesGrpc.getCommitMethod) == null) {
      synchronized (DataServicesGrpc.class) {
        if ((getCommitMethod = DataServicesGrpc.getCommitMethod) == null) {
          DataServicesGrpc.getCommitMethod = getCommitMethod =
              io.grpc.MethodDescriptor.<grpc.data.SyncSeq, grpc.data.SyncSeq>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Commit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.SyncSeq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.SyncSeq.getDefaultInstance()))
              .setSchemaDescriptor(new DataServicesMethodDescriptorSupplier("Commit"))
              .build();
        }
      }
    }
    return getCommitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.data.SyncSeq,
      grpc.data.SyncSeq> getRollBackMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RollBack",
      requestType = grpc.data.SyncSeq.class,
      responseType = grpc.data.SyncSeq.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.data.SyncSeq,
      grpc.data.SyncSeq> getRollBackMethod() {
    io.grpc.MethodDescriptor<grpc.data.SyncSeq, grpc.data.SyncSeq> getRollBackMethod;
    if ((getRollBackMethod = DataServicesGrpc.getRollBackMethod) == null) {
      synchronized (DataServicesGrpc.class) {
        if ((getRollBackMethod = DataServicesGrpc.getRollBackMethod) == null) {
          DataServicesGrpc.getRollBackMethod = getRollBackMethod =
              io.grpc.MethodDescriptor.<grpc.data.SyncSeq, grpc.data.SyncSeq>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RollBack"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.SyncSeq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.SyncSeq.getDefaultInstance()))
              .setSchemaDescriptor(new DataServicesMethodDescriptorSupplier("RollBack"))
              .build();
        }
      }
    }
    return getRollBackMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.data.SyncSeq,
      grpc.data.SyncSeq> getCheckLogMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CheckLog",
      requestType = grpc.data.SyncSeq.class,
      responseType = grpc.data.SyncSeq.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.data.SyncSeq,
      grpc.data.SyncSeq> getCheckLogMethod() {
    io.grpc.MethodDescriptor<grpc.data.SyncSeq, grpc.data.SyncSeq> getCheckLogMethod;
    if ((getCheckLogMethod = DataServicesGrpc.getCheckLogMethod) == null) {
      synchronized (DataServicesGrpc.class) {
        if ((getCheckLogMethod = DataServicesGrpc.getCheckLogMethod) == null) {
          DataServicesGrpc.getCheckLogMethod = getCheckLogMethod =
              io.grpc.MethodDescriptor.<grpc.data.SyncSeq, grpc.data.SyncSeq>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CheckLog"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.SyncSeq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.data.SyncSeq.getDefaultInstance()))
              .setSchemaDescriptor(new DataServicesMethodDescriptorSupplier("CheckLog"))
              .build();
        }
      }
    }
    return getCheckLogMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DataServicesStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataServicesStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataServicesStub>() {
        @java.lang.Override
        public DataServicesStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataServicesStub(channel, callOptions);
        }
      };
    return DataServicesStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DataServicesBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataServicesBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataServicesBlockingStub>() {
        @java.lang.Override
        public DataServicesBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataServicesBlockingStub(channel, callOptions);
        }
      };
    return DataServicesBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DataServicesFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataServicesFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataServicesFutureStub>() {
        @java.lang.Override
        public DataServicesFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataServicesFutureStub(channel, callOptions);
        }
      };
    return DataServicesFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class DataServicesImplBase implements io.grpc.BindableService {

    /**
     */
    public void put(grpc.data.PutRequest request,
        io.grpc.stub.StreamObserver<grpc.data.PutResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPutMethod(), responseObserver);
    }

    /**
     */
    public void get(grpc.data.GetRequest request,
        io.grpc.stub.StreamObserver<grpc.data.GetResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    /**
     */
    public void delete(grpc.data.DeleteRequest request,
        io.grpc.stub.StreamObserver<grpc.data.DeleteResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
    }

    /**
     */
    public void migrateFrom(grpc.data.MigrateRequest request,
        io.grpc.stub.StreamObserver<grpc.data.MigrateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getMigrateFromMethod(), responseObserver);
    }

    /**
     */
    public void migrateTo(grpc.data.MigrateRequest request,
        io.grpc.stub.StreamObserver<grpc.data.MigrateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getMigrateToMethod(), responseObserver);
    }

    /**
     */
    public void transferDataFrom(grpc.data.TransferDataFromRequest request,
        io.grpc.stub.StreamObserver<grpc.data.TransferDataFromResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getTransferDataFromMethod(), responseObserver);
    }

    /**
     */
    public void transferDataTo(grpc.data.TransferDataToRequest request,
        io.grpc.stub.StreamObserver<grpc.data.TransferDataToResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getTransferDataToMethod(), responseObserver);
    }

    /**
     */
    public void sync(grpc.data.SyncRequest request,
        io.grpc.stub.StreamObserver<grpc.data.SyncResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSyncMethod(), responseObserver);
    }

    /**
     */
    public void tryPut(grpc.data.TryPutRequest request,
        io.grpc.stub.StreamObserver<grpc.data.PutResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getTryPutMethod(), responseObserver);
    }

    /**
     */
    public void tryDelete(grpc.data.TryDeleteRequest request,
        io.grpc.stub.StreamObserver<grpc.data.DeleteResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getTryDeleteMethod(), responseObserver);
    }

    /**
     */
    public void commit(grpc.data.SyncSeq request,
        io.grpc.stub.StreamObserver<grpc.data.SyncSeq> responseObserver) {
      asyncUnimplementedUnaryCall(getCommitMethod(), responseObserver);
    }

    /**
     */
    public void rollBack(grpc.data.SyncSeq request,
        io.grpc.stub.StreamObserver<grpc.data.SyncSeq> responseObserver) {
      asyncUnimplementedUnaryCall(getRollBackMethod(), responseObserver);
    }

    /**
     */
    public void checkLog(grpc.data.SyncSeq request,
        io.grpc.stub.StreamObserver<grpc.data.SyncSeq> responseObserver) {
      asyncUnimplementedUnaryCall(getCheckLogMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPutMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                grpc.data.PutRequest,
                grpc.data.PutResponse>(
                  this, METHODID_PUT)))
          .addMethod(
            getGetMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                grpc.data.GetRequest,
                grpc.data.GetResponse>(
                  this, METHODID_GET)))
          .addMethod(
            getDeleteMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                grpc.data.DeleteRequest,
                grpc.data.DeleteResponse>(
                  this, METHODID_DELETE)))
          .addMethod(
            getMigrateFromMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                grpc.data.MigrateRequest,
                grpc.data.MigrateResponse>(
                  this, METHODID_MIGRATE_FROM)))
          .addMethod(
            getMigrateToMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                grpc.data.MigrateRequest,
                grpc.data.MigrateResponse>(
                  this, METHODID_MIGRATE_TO)))
          .addMethod(
            getTransferDataFromMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                grpc.data.TransferDataFromRequest,
                grpc.data.TransferDataFromResponse>(
                  this, METHODID_TRANSFER_DATA_FROM)))
          .addMethod(
            getTransferDataToMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                grpc.data.TransferDataToRequest,
                grpc.data.TransferDataToResponse>(
                  this, METHODID_TRANSFER_DATA_TO)))
          .addMethod(
            getSyncMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                grpc.data.SyncRequest,
                grpc.data.SyncResponse>(
                  this, METHODID_SYNC)))
          .addMethod(
            getTryPutMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                grpc.data.TryPutRequest,
                grpc.data.PutResponse>(
                  this, METHODID_TRY_PUT)))
          .addMethod(
            getTryDeleteMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                grpc.data.TryDeleteRequest,
                grpc.data.DeleteResponse>(
                  this, METHODID_TRY_DELETE)))
          .addMethod(
            getCommitMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                grpc.data.SyncSeq,
                grpc.data.SyncSeq>(
                  this, METHODID_COMMIT)))
          .addMethod(
            getRollBackMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                grpc.data.SyncSeq,
                grpc.data.SyncSeq>(
                  this, METHODID_ROLL_BACK)))
          .addMethod(
            getCheckLogMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                grpc.data.SyncSeq,
                grpc.data.SyncSeq>(
                  this, METHODID_CHECK_LOG)))
          .build();
    }
  }

  /**
   */
  public static final class DataServicesStub extends io.grpc.stub.AbstractAsyncStub<DataServicesStub> {
    private DataServicesStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataServicesStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataServicesStub(channel, callOptions);
    }

    /**
     */
    public void put(grpc.data.PutRequest request,
        io.grpc.stub.StreamObserver<grpc.data.PutResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPutMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void get(grpc.data.GetRequest request,
        io.grpc.stub.StreamObserver<grpc.data.GetResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void delete(grpc.data.DeleteRequest request,
        io.grpc.stub.StreamObserver<grpc.data.DeleteResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void migrateFrom(grpc.data.MigrateRequest request,
        io.grpc.stub.StreamObserver<grpc.data.MigrateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getMigrateFromMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void migrateTo(grpc.data.MigrateRequest request,
        io.grpc.stub.StreamObserver<grpc.data.MigrateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getMigrateToMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void transferDataFrom(grpc.data.TransferDataFromRequest request,
        io.grpc.stub.StreamObserver<grpc.data.TransferDataFromResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getTransferDataFromMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void transferDataTo(grpc.data.TransferDataToRequest request,
        io.grpc.stub.StreamObserver<grpc.data.TransferDataToResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getTransferDataToMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sync(grpc.data.SyncRequest request,
        io.grpc.stub.StreamObserver<grpc.data.SyncResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSyncMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void tryPut(grpc.data.TryPutRequest request,
        io.grpc.stub.StreamObserver<grpc.data.PutResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getTryPutMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void tryDelete(grpc.data.TryDeleteRequest request,
        io.grpc.stub.StreamObserver<grpc.data.DeleteResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getTryDeleteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void commit(grpc.data.SyncSeq request,
        io.grpc.stub.StreamObserver<grpc.data.SyncSeq> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCommitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rollBack(grpc.data.SyncSeq request,
        io.grpc.stub.StreamObserver<grpc.data.SyncSeq> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRollBackMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void checkLog(grpc.data.SyncSeq request,
        io.grpc.stub.StreamObserver<grpc.data.SyncSeq> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCheckLogMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DataServicesBlockingStub extends io.grpc.stub.AbstractBlockingStub<DataServicesBlockingStub> {
    private DataServicesBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataServicesBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataServicesBlockingStub(channel, callOptions);
    }

    /**
     */
    public grpc.data.PutResponse put(grpc.data.PutRequest request) {
      return blockingUnaryCall(
          getChannel(), getPutMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.data.GetResponse get(grpc.data.GetRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.data.DeleteResponse delete(grpc.data.DeleteRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.data.MigrateResponse migrateFrom(grpc.data.MigrateRequest request) {
      return blockingUnaryCall(
          getChannel(), getMigrateFromMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.data.MigrateResponse migrateTo(grpc.data.MigrateRequest request) {
      return blockingUnaryCall(
          getChannel(), getMigrateToMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.data.TransferDataFromResponse transferDataFrom(grpc.data.TransferDataFromRequest request) {
      return blockingUnaryCall(
          getChannel(), getTransferDataFromMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.data.TransferDataToResponse transferDataTo(grpc.data.TransferDataToRequest request) {
      return blockingUnaryCall(
          getChannel(), getTransferDataToMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.data.SyncResponse sync(grpc.data.SyncRequest request) {
      return blockingUnaryCall(
          getChannel(), getSyncMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.data.PutResponse tryPut(grpc.data.TryPutRequest request) {
      return blockingUnaryCall(
          getChannel(), getTryPutMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.data.DeleteResponse tryDelete(grpc.data.TryDeleteRequest request) {
      return blockingUnaryCall(
          getChannel(), getTryDeleteMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.data.SyncSeq commit(grpc.data.SyncSeq request) {
      return blockingUnaryCall(
          getChannel(), getCommitMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.data.SyncSeq rollBack(grpc.data.SyncSeq request) {
      return blockingUnaryCall(
          getChannel(), getRollBackMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.data.SyncSeq checkLog(grpc.data.SyncSeq request) {
      return blockingUnaryCall(
          getChannel(), getCheckLogMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DataServicesFutureStub extends io.grpc.stub.AbstractFutureStub<DataServicesFutureStub> {
    private DataServicesFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataServicesFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataServicesFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.data.PutResponse> put(
        grpc.data.PutRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPutMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.data.GetResponse> get(
        grpc.data.GetRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.data.DeleteResponse> delete(
        grpc.data.DeleteRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.data.MigrateResponse> migrateFrom(
        grpc.data.MigrateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getMigrateFromMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.data.MigrateResponse> migrateTo(
        grpc.data.MigrateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getMigrateToMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.data.TransferDataFromResponse> transferDataFrom(
        grpc.data.TransferDataFromRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getTransferDataFromMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.data.TransferDataToResponse> transferDataTo(
        grpc.data.TransferDataToRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getTransferDataToMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.data.SyncResponse> sync(
        grpc.data.SyncRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSyncMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.data.PutResponse> tryPut(
        grpc.data.TryPutRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getTryPutMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.data.DeleteResponse> tryDelete(
        grpc.data.TryDeleteRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getTryDeleteMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.data.SyncSeq> commit(
        grpc.data.SyncSeq request) {
      return futureUnaryCall(
          getChannel().newCall(getCommitMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.data.SyncSeq> rollBack(
        grpc.data.SyncSeq request) {
      return futureUnaryCall(
          getChannel().newCall(getRollBackMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.data.SyncSeq> checkLog(
        grpc.data.SyncSeq request) {
      return futureUnaryCall(
          getChannel().newCall(getCheckLogMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PUT = 0;
  private static final int METHODID_GET = 1;
  private static final int METHODID_DELETE = 2;
  private static final int METHODID_MIGRATE_FROM = 3;
  private static final int METHODID_MIGRATE_TO = 4;
  private static final int METHODID_TRANSFER_DATA_FROM = 5;
  private static final int METHODID_TRANSFER_DATA_TO = 6;
  private static final int METHODID_SYNC = 7;
  private static final int METHODID_TRY_PUT = 8;
  private static final int METHODID_TRY_DELETE = 9;
  private static final int METHODID_COMMIT = 10;
  private static final int METHODID_ROLL_BACK = 11;
  private static final int METHODID_CHECK_LOG = 12;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DataServicesImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DataServicesImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PUT:
          serviceImpl.put((grpc.data.PutRequest) request,
              (io.grpc.stub.StreamObserver<grpc.data.PutResponse>) responseObserver);
          break;
        case METHODID_GET:
          serviceImpl.get((grpc.data.GetRequest) request,
              (io.grpc.stub.StreamObserver<grpc.data.GetResponse>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((grpc.data.DeleteRequest) request,
              (io.grpc.stub.StreamObserver<grpc.data.DeleteResponse>) responseObserver);
          break;
        case METHODID_MIGRATE_FROM:
          serviceImpl.migrateFrom((grpc.data.MigrateRequest) request,
              (io.grpc.stub.StreamObserver<grpc.data.MigrateResponse>) responseObserver);
          break;
        case METHODID_MIGRATE_TO:
          serviceImpl.migrateTo((grpc.data.MigrateRequest) request,
              (io.grpc.stub.StreamObserver<grpc.data.MigrateResponse>) responseObserver);
          break;
        case METHODID_TRANSFER_DATA_FROM:
          serviceImpl.transferDataFrom((grpc.data.TransferDataFromRequest) request,
              (io.grpc.stub.StreamObserver<grpc.data.TransferDataFromResponse>) responseObserver);
          break;
        case METHODID_TRANSFER_DATA_TO:
          serviceImpl.transferDataTo((grpc.data.TransferDataToRequest) request,
              (io.grpc.stub.StreamObserver<grpc.data.TransferDataToResponse>) responseObserver);
          break;
        case METHODID_SYNC:
          serviceImpl.sync((grpc.data.SyncRequest) request,
              (io.grpc.stub.StreamObserver<grpc.data.SyncResponse>) responseObserver);
          break;
        case METHODID_TRY_PUT:
          serviceImpl.tryPut((grpc.data.TryPutRequest) request,
              (io.grpc.stub.StreamObserver<grpc.data.PutResponse>) responseObserver);
          break;
        case METHODID_TRY_DELETE:
          serviceImpl.tryDelete((grpc.data.TryDeleteRequest) request,
              (io.grpc.stub.StreamObserver<grpc.data.DeleteResponse>) responseObserver);
          break;
        case METHODID_COMMIT:
          serviceImpl.commit((grpc.data.SyncSeq) request,
              (io.grpc.stub.StreamObserver<grpc.data.SyncSeq>) responseObserver);
          break;
        case METHODID_ROLL_BACK:
          serviceImpl.rollBack((grpc.data.SyncSeq) request,
              (io.grpc.stub.StreamObserver<grpc.data.SyncSeq>) responseObserver);
          break;
        case METHODID_CHECK_LOG:
          serviceImpl.checkLog((grpc.data.SyncSeq) request,
              (io.grpc.stub.StreamObserver<grpc.data.SyncSeq>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class DataServicesBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DataServicesBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.data.DataServicesProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DataServices");
    }
  }

  private static final class DataServicesFileDescriptorSupplier
      extends DataServicesBaseDescriptorSupplier {
    DataServicesFileDescriptorSupplier() {}
  }

  private static final class DataServicesMethodDescriptorSupplier
      extends DataServicesBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DataServicesMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DataServicesGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DataServicesFileDescriptorSupplier())
              .addMethod(getPutMethod())
              .addMethod(getGetMethod())
              .addMethod(getDeleteMethod())
              .addMethod(getMigrateFromMethod())
              .addMethod(getMigrateToMethod())
              .addMethod(getTransferDataFromMethod())
              .addMethod(getTransferDataToMethod())
              .addMethod(getSyncMethod())
              .addMethod(getTryPutMethod())
              .addMethod(getTryDeleteMethod())
              .addMethod(getCommitMethod())
              .addMethod(getRollBackMethod())
              .addMethod(getCheckLogMethod())
              .build();
        }
      }
    }
    return result;
  }
}
