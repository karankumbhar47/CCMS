package com.example.openapi.model;

import java.net.URI;
import java.util.Objects;
import com.example.openapi.model.OrderItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Order
 */

@JsonTypeName("order")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-12T04:08:19.693566868+05:30[Asia/Kolkata]")
public class Order {

  private String orderId;

  private String customerName;

  private String deliveryAddress;

  private String pickupLocation;

  @Valid
  private List<@Valid OrderItem> orderDetails;

  public Order orderId(String orderId) {
    this.orderId = orderId;
    return this;
  }

  /**
   * Get orderId
   * @return orderId
  */
  
  @Schema(name = "orderId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("orderId")
  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Order customerName(String customerName) {
    this.customerName = customerName;
    return this;
  }

  /**
   * Get customerName
   * @return customerName
  */
  
  @Schema(name = "customerName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("customerName")
  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public Order deliveryAddress(String deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
    return this;
  }

  /**
   * Get deliveryAddress
   * @return deliveryAddress
  */
  
  @Schema(name = "deliveryAddress", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("deliveryAddress")
  public String getDeliveryAddress() {
    return deliveryAddress;
  }

  public void setDeliveryAddress(String deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }

  public Order pickupLocation(String pickupLocation) {
    this.pickupLocation = pickupLocation;
    return this;
  }

  /**
   * Get pickupLocation
   * @return pickupLocation
  */
  
  @Schema(name = "pickupLocation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pickupLocation")
  public String getPickupLocation() {
    return pickupLocation;
  }

  public void setPickupLocation(String pickupLocation) {
    this.pickupLocation = pickupLocation;
  }

  public Order orderDetails(List<@Valid OrderItem> orderDetails) {
    this.orderDetails = orderDetails;
    return this;
  }

  public Order addOrderDetailsItem(OrderItem orderDetailsItem) {
    if (this.orderDetails == null) {
      this.orderDetails = new ArrayList<>();
    }
    this.orderDetails.add(orderDetailsItem);
    return this;
  }

  /**
   * Get orderDetails
   * @return orderDetails
  */
  @Valid 
  @Schema(name = "orderDetails", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("orderDetails")
  public List<@Valid OrderItem> getOrderDetails() {
    return orderDetails;
  }

  public void setOrderDetails(List<@Valid OrderItem> orderDetails) {
    this.orderDetails = orderDetails;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return Objects.equals(this.orderId, order.orderId) &&
        Objects.equals(this.customerName, order.customerName) &&
        Objects.equals(this.deliveryAddress, order.deliveryAddress) &&
        Objects.equals(this.pickupLocation, order.pickupLocation) &&
        Objects.equals(this.orderDetails, order.orderDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, customerName, deliveryAddress, pickupLocation, orderDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Order {\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    customerName: ").append(toIndentedString(customerName)).append("\n");
    sb.append("    deliveryAddress: ").append(toIndentedString(deliveryAddress)).append("\n");
    sb.append("    pickupLocation: ").append(toIndentedString(pickupLocation)).append("\n");
    sb.append("    orderDetails: ").append(toIndentedString(orderDetails)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

