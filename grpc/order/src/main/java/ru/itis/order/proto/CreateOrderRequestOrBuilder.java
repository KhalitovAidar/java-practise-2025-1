// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: user_order.proto

package ru.itis.order.proto;

public interface CreateOrderRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:CreateOrderRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string product_name = 1;</code>
   * @return The productName.
   */
  java.lang.String getProductName();
  /**
   * <code>string product_name = 1;</code>
   * @return The bytes for productName.
   */
  com.google.protobuf.ByteString
      getProductNameBytes();

  /**
   * <code>int64 user_id = 2;</code>
   * @return The userId.
   */
  long getUserId();
}
