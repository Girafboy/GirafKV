package grpc.locator;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.30.0)",
    comments = "Source: locatorservices.proto")
public final class LocatorServicesGrpc {

  private LocatorServicesGrpc() {}

  public static final String SERVICE_NAME = "locatorservices.LocatorServices";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.locator.LocateRequest,
      grpc.locator.LocateResponse> getLocateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Locate",
      requestType = grpc.locator.LocateRequest.class,
      responseType = grpc.locator.LocateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.locator.LocateRequest,
      grpc.locator.LocateResponse> getLocateMethod() {
    io.grpc.MethodDescriptor<grpc.locator.LocateRequest, grpc.locator.LocateResponse> getLocateMethod;
    if ((getLocateMethod = LocatorServicesGrpc.getLocateMethod) == null) {
      synchronized (LocatorServicesGrpc.class) {
        if ((getLocateMethod = LocatorServicesGrpc.getLocateMethod) == null) {
          LocatorServicesGrpc.getLocateMethod = getLocateMethod =
              io.grpc.MethodDescriptor.<grpc.locator.LocateRequest, grpc.locator.LocateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Locate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.locator.LocateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.locator.LocateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LocatorServicesMethodDescriptorSupplier("Locate"))
              .build();
        }
      }
    }
    return getLocateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LocatorServicesStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LocatorServicesStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LocatorServicesStub>() {
        @java.lang.Override
        public LocatorServicesStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LocatorServicesStub(channel, callOptions);
        }
      };
    return LocatorServicesStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LocatorServicesBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LocatorServicesBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LocatorServicesBlockingStub>() {
        @java.lang.Override
        public LocatorServicesBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LocatorServicesBlockingStub(channel, callOptions);
        }
      };
    return LocatorServicesBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LocatorServicesFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LocatorServicesFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LocatorServicesFutureStub>() {
        @java.lang.Override
        public LocatorServicesFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LocatorServicesFutureStub(channel, callOptions);
        }
      };
    return LocatorServicesFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class LocatorServicesImplBase implements io.grpc.BindableService {

    /**
     */
    public void locate(grpc.locator.LocateRequest request,
        io.grpc.stub.StreamObserver<grpc.locator.LocateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getLocateMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getLocateMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                grpc.locator.LocateRequest,
                grpc.locator.LocateResponse>(
                  this, METHODID_LOCATE)))
          .build();
    }
  }

  /**
   */
  public static final class LocatorServicesStub extends io.grpc.stub.AbstractAsyncStub<LocatorServicesStub> {
    private LocatorServicesStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LocatorServicesStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LocatorServicesStub(channel, callOptions);
    }

    /**
     */
    public void locate(grpc.locator.LocateRequest request,
        io.grpc.stub.StreamObserver<grpc.locator.LocateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getLocateMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class LocatorServicesBlockingStub extends io.grpc.stub.AbstractBlockingStub<LocatorServicesBlockingStub> {
    private LocatorServicesBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LocatorServicesBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LocatorServicesBlockingStub(channel, callOptions);
    }

    /**
     */
    public grpc.locator.LocateResponse locate(grpc.locator.LocateRequest request) {
      return blockingUnaryCall(
          getChannel(), getLocateMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class LocatorServicesFutureStub extends io.grpc.stub.AbstractFutureStub<LocatorServicesFutureStub> {
    private LocatorServicesFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LocatorServicesFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LocatorServicesFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.locator.LocateResponse> locate(
        grpc.locator.LocateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getLocateMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOCATE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LocatorServicesImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LocatorServicesImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LOCATE:
          serviceImpl.locate((grpc.locator.LocateRequest) request,
              (io.grpc.stub.StreamObserver<grpc.locator.LocateResponse>) responseObserver);
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

  private static abstract class LocatorServicesBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LocatorServicesBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.locator.LocatorServicesProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LocatorServices");
    }
  }

  private static final class LocatorServicesFileDescriptorSupplier
      extends LocatorServicesBaseDescriptorSupplier {
    LocatorServicesFileDescriptorSupplier() {}
  }

  private static final class LocatorServicesMethodDescriptorSupplier
      extends LocatorServicesBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LocatorServicesMethodDescriptorSupplier(String methodName) {
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
      synchronized (LocatorServicesGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LocatorServicesFileDescriptorSupplier())
              .addMethod(getLocateMethod())
              .build();
        }
      }
    }
    return result;
  }
}
