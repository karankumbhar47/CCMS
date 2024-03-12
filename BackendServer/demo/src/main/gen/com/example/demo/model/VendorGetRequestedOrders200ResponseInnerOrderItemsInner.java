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
 * VendorGetRequestedOrders200ResponseInnerOrderItemsInner
 */

@JsonTypeName("vendorGetRequestedOrders_200_response_inner_orderItems_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-12T16:44:30.158452179+05:30[Asia/Kolkata]")
public class VendorGetRequestedOrders200ResponseInnerOrderItemsInner implements Serializable {

  private static final long serialVersionUID = 1L;

  private String itemId;

  private Integer quantity;

  public VendorGetRequestedOrders200ResponseInnerOrderItemsInner() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public VendorGetRequestedOrders200ResponseInnerOrderItemsInner(String itemId, Integer quantity) {
    this.itemId = itemId;
    this.quantity = quantity;
  }

  public VendorGetRequestedOrders200ResponseInnerOrderItemsInner itemId(String itemId) {
    this.itemId = itemId;
    return this;
  }

  /**
   * Get itemId
   * @return itemId
  */
  @NotNull @Size(min = 40, max = 40) 
  @Schema(name = "itemId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("itemId")
  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public VendorGetRequestedOrders200ResponseInnerOrderItemsInner quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Quantity of the specific item.
   * @return quantity
  */
  @NotNull 
  @Schema(name = "quantity", description = "Quantity of the specific item.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("quantity")
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VendorGetRequestedOrders200ResponseInnerOrderItemsInner vendorGetRequestedOrders200ResponseInnerOrderItemsInner = (VendorGetRequestedOrders200ResponseInnerOrderItemsInner) o;
    return Objects.equals(this.itemId, vendorGetRequestedOrders200ResponseInnerOrderItemsInner.itemId) &&
        Objects.equals(this.quantity, vendorGetRequestedOrders200ResponseInnerOrderItemsInner.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, quantity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VendorGetRequestedOrders200ResponseInnerOrderItemsInner {\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
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

