package com.example.openapi.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * OrderItem
 */

@JsonTypeName("orderItem")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-12T04:08:19.693566868+05:30[Asia/Kolkata]")
public class OrderItem {

  private String itemId;

  private String itemName;

  private Integer quantity;

  private BigDecimal price;

  public OrderItem itemId(String itemId) {
    this.itemId = itemId;
    return this;
  }

  /**
   * Get itemId
   * @return itemId
  */
  
  @Schema(name = "itemId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("itemId")
  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public OrderItem itemName(String itemName) {
    this.itemName = itemName;
    return this;
  }

  /**
   * Get itemName
   * @return itemName
  */
  
  @Schema(name = "itemName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("itemName")
  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public OrderItem quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Get quantity
   * @return quantity
  */
  
  @Schema(name = "quantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("quantity")
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public OrderItem price(BigDecimal price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
  */
  @Valid 
  @Schema(name = "price", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("price")
  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderItem orderItem = (OrderItem) o;
    return Objects.equals(this.itemId, orderItem.itemId) &&
        Objects.equals(this.itemName, orderItem.itemName) &&
        Objects.equals(this.quantity, orderItem.quantity) &&
        Objects.equals(this.price, orderItem.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, itemName, quantity, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderItem {\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
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

