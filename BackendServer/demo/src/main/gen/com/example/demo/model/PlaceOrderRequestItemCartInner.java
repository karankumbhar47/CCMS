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
 * PlaceOrderRequestItemCartInner
 */

@JsonTypeName("placeOrder_request_itemCart_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-12T16:44:30.158452179+05:30[Asia/Kolkata]")
public class PlaceOrderRequestItemCartInner implements Serializable {

  private static final long serialVersionUID = 1L;

  private String itemId;

  private Integer quantity;

  public PlaceOrderRequestItemCartInner() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PlaceOrderRequestItemCartInner(String itemId, Integer quantity) {
    this.itemId = itemId;
    this.quantity = quantity;
  }

  public PlaceOrderRequestItemCartInner itemId(String itemId) {
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

  public PlaceOrderRequestItemCartInner quantity(Integer quantity) {
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
    PlaceOrderRequestItemCartInner placeOrderRequestItemCartInner = (PlaceOrderRequestItemCartInner) o;
    return Objects.equals(this.itemId, placeOrderRequestItemCartInner.itemId) &&
        Objects.equals(this.quantity, placeOrderRequestItemCartInner.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, quantity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PlaceOrderRequestItemCartInner {\n");
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

