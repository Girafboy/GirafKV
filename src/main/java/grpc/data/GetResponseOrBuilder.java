// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: dataservices.proto

package grpc.data;

public interface GetResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:dataservices.GetResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 status = 1;</code>
   * @return The status.
   */
  int getStatus();

  /**
   * <code>string value = 2;</code>
   * @return The value.
   */
  java.lang.String getValue();
  /**
   * <code>string value = 2;</code>
   * @return The bytes for value.
   */
  com.google.protobuf.ByteString
      getValueBytes();
}