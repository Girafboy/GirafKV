// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: locatorservices.proto

package grpc.locator;

public final class LocatorServicesProto {
  private LocatorServicesProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_locatorservices_LocateRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_locatorservices_LocateRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_locatorservices_LocateResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_locatorservices_LocateResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\025locatorservices.proto\022\017locatorservices" +
      "\"-\n\rLocateRequest\022\013\n\003key\030\001 \001(\t\022\017\n\007isWrit" +
      "e\030\002 \001(\005\"1\n\016LocateResponse\022\016\n\006status\030\001 \001(" +
      "\005\022\017\n\007address\030\002 \001(\t2^\n\017LocatorServices\022K\n" +
      "\006Locate\022\036.locatorservices.LocateRequest\032" +
      "\037.locatorservices.LocateResponse\"\000B,\n\014gr" +
      "pc.locatorB\024LocatorServicesProtoP\001\242\002\003HLW" +
      "b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_locatorservices_LocateRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_locatorservices_LocateRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_locatorservices_LocateRequest_descriptor,
        new java.lang.String[] { "Key", "IsWrite", });
    internal_static_locatorservices_LocateResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_locatorservices_LocateResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_locatorservices_LocateResponse_descriptor,
        new java.lang.String[] { "Status", "Address", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
