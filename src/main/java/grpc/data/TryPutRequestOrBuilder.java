// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: dataservices.proto

package grpc.data;

public interface TryPutRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:dataservices.TryPutRequest)
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

  /**
   * <code>int32 sync_seq = 3;</code>
   * @return The syncSeq.
   */
  int getSyncSeq();
}
