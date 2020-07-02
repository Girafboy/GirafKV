// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: dataservices.proto

package grpc.data;

/**
 * Protobuf type {@code dataservices.MigrateSlice}
 */
public final class MigrateSlice extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:dataservices.MigrateSlice)
    MigrateSliceOrBuilder {
private static final long serialVersionUID = 0L;
  // Use MigrateSlice.newBuilder() to construct.
  private MigrateSlice(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private MigrateSlice() {
    address_ = "";
    slotId_ = emptyIntList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new MigrateSlice();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private MigrateSlice(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            address_ = s;
            break;
          }
          case 16: {
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              slotId_ = newIntList();
              mutable_bitField0_ |= 0x00000001;
            }
            slotId_.addInt(input.readInt32());
            break;
          }
          case 18: {
            int length = input.readRawVarint32();
            int limit = input.pushLimit(length);
            if (!((mutable_bitField0_ & 0x00000001) != 0) && input.getBytesUntilLimit() > 0) {
              slotId_ = newIntList();
              mutable_bitField0_ |= 0x00000001;
            }
            while (input.getBytesUntilLimit() > 0) {
              slotId_.addInt(input.readInt32());
            }
            input.popLimit(limit);
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        slotId_.makeImmutable(); // C
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return grpc.data.DataServicesProto.internal_static_dataservices_MigrateSlice_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return grpc.data.DataServicesProto.internal_static_dataservices_MigrateSlice_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            grpc.data.MigrateSlice.class, grpc.data.MigrateSlice.Builder.class);
  }

  public static final int ADDRESS_FIELD_NUMBER = 1;
  private volatile java.lang.Object address_;
  /**
   * <code>string address = 1;</code>
   * @return The address.
   */
  @java.lang.Override
  public java.lang.String getAddress() {
    java.lang.Object ref = address_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      address_ = s;
      return s;
    }
  }
  /**
   * <code>string address = 1;</code>
   * @return The bytes for address.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getAddressBytes() {
    java.lang.Object ref = address_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      address_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int SLOT_ID_FIELD_NUMBER = 2;
  private com.google.protobuf.Internal.IntList slotId_;
  /**
   * <code>repeated int32 slot_id = 2;</code>
   * @return A list containing the slotId.
   */
  @java.lang.Override
  public java.util.List<java.lang.Integer>
      getSlotIdList() {
    return slotId_;
  }
  /**
   * <code>repeated int32 slot_id = 2;</code>
   * @return The count of slotId.
   */
  public int getSlotIdCount() {
    return slotId_.size();
  }
  /**
   * <code>repeated int32 slot_id = 2;</code>
   * @param index The index of the element to return.
   * @return The slotId at the given index.
   */
  public int getSlotId(int index) {
    return slotId_.getInt(index);
  }
  private int slotIdMemoizedSerializedSize = -1;

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    getSerializedSize();
    if (!getAddressBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, address_);
    }
    if (getSlotIdList().size() > 0) {
      output.writeUInt32NoTag(18);
      output.writeUInt32NoTag(slotIdMemoizedSerializedSize);
    }
    for (int i = 0; i < slotId_.size(); i++) {
      output.writeInt32NoTag(slotId_.getInt(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getAddressBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, address_);
    }
    {
      int dataSize = 0;
      for (int i = 0; i < slotId_.size(); i++) {
        dataSize += com.google.protobuf.CodedOutputStream
          .computeInt32SizeNoTag(slotId_.getInt(i));
      }
      size += dataSize;
      if (!getSlotIdList().isEmpty()) {
        size += 1;
        size += com.google.protobuf.CodedOutputStream
            .computeInt32SizeNoTag(dataSize);
      }
      slotIdMemoizedSerializedSize = dataSize;
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof grpc.data.MigrateSlice)) {
      return super.equals(obj);
    }
    grpc.data.MigrateSlice other = (grpc.data.MigrateSlice) obj;

    if (!getAddress()
        .equals(other.getAddress())) return false;
    if (!getSlotIdList()
        .equals(other.getSlotIdList())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + ADDRESS_FIELD_NUMBER;
    hash = (53 * hash) + getAddress().hashCode();
    if (getSlotIdCount() > 0) {
      hash = (37 * hash) + SLOT_ID_FIELD_NUMBER;
      hash = (53 * hash) + getSlotIdList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static grpc.data.MigrateSlice parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.data.MigrateSlice parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.data.MigrateSlice parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.data.MigrateSlice parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.data.MigrateSlice parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.data.MigrateSlice parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.data.MigrateSlice parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static grpc.data.MigrateSlice parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static grpc.data.MigrateSlice parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static grpc.data.MigrateSlice parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static grpc.data.MigrateSlice parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static grpc.data.MigrateSlice parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(grpc.data.MigrateSlice prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code dataservices.MigrateSlice}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:dataservices.MigrateSlice)
      grpc.data.MigrateSliceOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return grpc.data.DataServicesProto.internal_static_dataservices_MigrateSlice_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return grpc.data.DataServicesProto.internal_static_dataservices_MigrateSlice_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              grpc.data.MigrateSlice.class, grpc.data.MigrateSlice.Builder.class);
    }

    // Construct using grpc.data.MigrateSlice.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      address_ = "";

      slotId_ = emptyIntList();
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return grpc.data.DataServicesProto.internal_static_dataservices_MigrateSlice_descriptor;
    }

    @java.lang.Override
    public grpc.data.MigrateSlice getDefaultInstanceForType() {
      return grpc.data.MigrateSlice.getDefaultInstance();
    }

    @java.lang.Override
    public grpc.data.MigrateSlice build() {
      grpc.data.MigrateSlice result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public grpc.data.MigrateSlice buildPartial() {
      grpc.data.MigrateSlice result = new grpc.data.MigrateSlice(this);
      int from_bitField0_ = bitField0_;
      result.address_ = address_;
      if (((bitField0_ & 0x00000001) != 0)) {
        slotId_.makeImmutable();
        bitField0_ = (bitField0_ & ~0x00000001);
      }
      result.slotId_ = slotId_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof grpc.data.MigrateSlice) {
        return mergeFrom((grpc.data.MigrateSlice)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(grpc.data.MigrateSlice other) {
      if (other == grpc.data.MigrateSlice.getDefaultInstance()) return this;
      if (!other.getAddress().isEmpty()) {
        address_ = other.address_;
        onChanged();
      }
      if (!other.slotId_.isEmpty()) {
        if (slotId_.isEmpty()) {
          slotId_ = other.slotId_;
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          ensureSlotIdIsMutable();
          slotId_.addAll(other.slotId_);
        }
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      grpc.data.MigrateSlice parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (grpc.data.MigrateSlice) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.lang.Object address_ = "";
    /**
     * <code>string address = 1;</code>
     * @return The address.
     */
    public java.lang.String getAddress() {
      java.lang.Object ref = address_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        address_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string address = 1;</code>
     * @return The bytes for address.
     */
    public com.google.protobuf.ByteString
        getAddressBytes() {
      java.lang.Object ref = address_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        address_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string address = 1;</code>
     * @param value The address to set.
     * @return This builder for chaining.
     */
    public Builder setAddress(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      address_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string address = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearAddress() {
      
      address_ = getDefaultInstance().getAddress();
      onChanged();
      return this;
    }
    /**
     * <code>string address = 1;</code>
     * @param value The bytes for address to set.
     * @return This builder for chaining.
     */
    public Builder setAddressBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      address_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.Internal.IntList slotId_ = emptyIntList();
    private void ensureSlotIdIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        slotId_ = mutableCopy(slotId_);
        bitField0_ |= 0x00000001;
       }
    }
    /**
     * <code>repeated int32 slot_id = 2;</code>
     * @return A list containing the slotId.
     */
    public java.util.List<java.lang.Integer>
        getSlotIdList() {
      return ((bitField0_ & 0x00000001) != 0) ?
               java.util.Collections.unmodifiableList(slotId_) : slotId_;
    }
    /**
     * <code>repeated int32 slot_id = 2;</code>
     * @return The count of slotId.
     */
    public int getSlotIdCount() {
      return slotId_.size();
    }
    /**
     * <code>repeated int32 slot_id = 2;</code>
     * @param index The index of the element to return.
     * @return The slotId at the given index.
     */
    public int getSlotId(int index) {
      return slotId_.getInt(index);
    }
    /**
     * <code>repeated int32 slot_id = 2;</code>
     * @param index The index to set the value at.
     * @param value The slotId to set.
     * @return This builder for chaining.
     */
    public Builder setSlotId(
        int index, int value) {
      ensureSlotIdIsMutable();
      slotId_.setInt(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int32 slot_id = 2;</code>
     * @param value The slotId to add.
     * @return This builder for chaining.
     */
    public Builder addSlotId(int value) {
      ensureSlotIdIsMutable();
      slotId_.addInt(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int32 slot_id = 2;</code>
     * @param values The slotId to add.
     * @return This builder for chaining.
     */
    public Builder addAllSlotId(
        java.lang.Iterable<? extends java.lang.Integer> values) {
      ensureSlotIdIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, slotId_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int32 slot_id = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearSlotId() {
      slotId_ = emptyIntList();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:dataservices.MigrateSlice)
  }

  // @@protoc_insertion_point(class_scope:dataservices.MigrateSlice)
  private static final grpc.data.MigrateSlice DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new grpc.data.MigrateSlice();
  }

  public static grpc.data.MigrateSlice getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MigrateSlice>
      PARSER = new com.google.protobuf.AbstractParser<MigrateSlice>() {
    @java.lang.Override
    public MigrateSlice parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new MigrateSlice(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<MigrateSlice> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<MigrateSlice> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public grpc.data.MigrateSlice getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

