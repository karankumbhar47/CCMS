package com.example.openapi.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * DeliveryAcceptOrderRequest
 */

@JsonTypeName("deliveryAcceptOrder_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-12T04:08:19.693566868+05:30[Asia/Kolkata]")
public class DeliveryAcceptOrderRequest {

  private String orderId;

  public DeliveryAcceptOrderRequest orderId(String orderId) {
    this.orderId = orderId;
    return this;
  }

  /**
   * Get orderId
   * @return orderId
  */
  @Size(min = 40, max = 40) 
  @Schema(name = "orderId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("orderId")
  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeliveryAcceptOrderRequest deliveryAcceptOrderRequest = (DeliveryAcceptOrderRequest) o;
    return Objects.equals(this.orderId, deliveryAcceptOrderRequest.orderId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeliveryAcceptOrderRequest {\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
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

