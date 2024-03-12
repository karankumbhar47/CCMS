package com.example.demo.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * DeliveryDropRequest
 */

@JsonTypeName("deliveryDrop_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-12T16:44:30.158452179+05:30[Asia/Kolkata]")
public class DeliveryDropRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String orderId;

  private Integer otp;

  public DeliveryDropRequest orderId(String orderId) {
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

  public DeliveryDropRequest otp(Integer otp) {
    this.otp = otp;
    return this;
  }

  /**
   * Get otp
   * @return otp
  */
  
  @Schema(name = "otp", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("otp")
  public Integer getOtp() {
    return otp;
  }

  public void setOtp(Integer otp) {
    this.otp = otp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeliveryDropRequest deliveryDropRequest = (DeliveryDropRequest) o;
    return Objects.equals(this.orderId, deliveryDropRequest.orderId) &&
        Objects.equals(this.otp, deliveryDropRequest.otp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, otp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeliveryDropRequest {\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    otp: ").append(toIndentedString(otp)).append("\n");
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

