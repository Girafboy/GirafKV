// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: dataservices.proto

package grpc.data;

public interface SlotOrBuilder extends
    // @@protoc_insertion_point(interface_extends:dataservices.Slot)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 slot_id = 1;</code>
   * @return The slotId.
   */
  int getSlotId();

  /**
   * <code>repeated .dataservices.Entry entries = 2;</code>
   */
  java.util.List<grpc.data.Entry> 
      getEntriesList();
  /**
   * <code>repeated .dataservices.Entry entries = 2;</code>
   */
  grpc.data.Entry getEntries(int index);
  /**
   * <code>repeated .dataservices.Entry entries = 2;</code>
   */
  int getEntriesCount();
  /**
   * <code>repeated .dataservices.Entry entries = 2;</code>
   */
  java.util.List<? extends grpc.data.EntryOrBuilder> 
      getEntriesOrBuilderList();
  /**
   * <code>repeated .dataservices.Entry entries = 2;</code>
   */
  grpc.data.EntryOrBuilder getEntriesOrBuilder(
      int index);
}
