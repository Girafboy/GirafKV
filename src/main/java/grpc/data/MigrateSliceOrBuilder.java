// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: dataservices.proto

package grpc.data;

public interface MigrateSliceOrBuilder extends
    // @@protoc_insertion_point(interface_extends:dataservices.MigrateSlice)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string address = 1;</code>
   * @return The address.
   */
  java.lang.String getAddress();
  /**
   * <code>string address = 1;</code>
   * @return The bytes for address.
   */
  com.google.protobuf.ByteString
      getAddressBytes();

  /**
   * <code>repeated int32 slot_id = 2;</code>
   * @return A list containing the slotId.
   */
  java.util.List<java.lang.Integer> getSlotIdList();
  /**
   * <code>repeated int32 slot_id = 2;</code>
   * @return The count of slotId.
   */
  int getSlotIdCount();
  /**
   * <code>repeated int32 slot_id = 2;</code>
   * @param index The index of the element to return.
   * @return The slotId at the given index.
   */
  int getSlotId(int index);
}
