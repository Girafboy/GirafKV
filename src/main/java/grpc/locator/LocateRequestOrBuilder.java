// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: locatorservices.proto

package grpc.locator;

public interface LocateRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:locatorservices.LocateRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string key = 1;</code>
   * @return The key.
   */
  java.lang.String getKey();
  /**
   * <code>string key = 1;</code>
   * @return The bytes for key.
   */
  com.google.protobuf.ByteString
      getKeyBytes();

  /**
   * <code>int32 isWrite = 2;</code>
   * @return The isWrite.
   */
  int getIsWrite();
}