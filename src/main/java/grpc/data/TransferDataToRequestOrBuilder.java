// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: dataservices.proto

package grpc.data;

public interface TransferDataToRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:dataservices.TransferDataToRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .dataservices.Entry entries = 1;</code>
   */
  java.util.List<grpc.data.Entry> 
      getEntriesList();
  /**
   * <code>repeated .dataservices.Entry entries = 1;</code>
   */
  grpc.data.Entry getEntries(int index);
  /**
   * <code>repeated .dataservices.Entry entries = 1;</code>
   */
  int getEntriesCount();
  /**
   * <code>repeated .dataservices.Entry entries = 1;</code>
   */
  java.util.List<? extends grpc.data.EntryOrBuilder> 
      getEntriesOrBuilderList();
  /**
   * <code>repeated .dataservices.Entry entries = 1;</code>
   */
  grpc.data.EntryOrBuilder getEntriesOrBuilder(
      int index);
}
