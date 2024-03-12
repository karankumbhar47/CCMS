package com.example.openapi.model;

import java.net.URI;
import java.util.Objects;
import com.example.openapi.model.PlaceOrderRequestItemCartInner;
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
 * PlaceOrderRequest
 */

@JsonTypeName("placeOrder_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-12T04:08:19.693566868+05:30[Asia/Kolkata]")
public class PlaceOrderRequest {

  @Valid
  private List<@Valid PlaceOrderRequestItemCartInner> itemCart = new ArrayList<>();

  private String location;

  public PlaceOrderRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PlaceOrderRequest(List<@Valid PlaceOrderRequestItemCartInner> itemCart, String location) {
    this.itemCart = itemCart;
    this.location = location;
  }

  public PlaceOrderRequest itemCart(List<@Valid PlaceOrderRequestItemCartInner> itemCart) {
    this.itemCart = itemCart;
    return this;
  }

  public PlaceOrderRequest addItemCartItem(PlaceOrderRequestItemCartInner itemCartItem) {
    if (this.itemCart == null) {
      this.itemCart = new ArrayList<>();
    }
    this.itemCart.add(itemCartItem);
    return this;
  }

  /**
   * Get itemCart
   * @return itemCart
  */
  @NotNull @Valid 
  @Schema(name = "itemCart", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("itemCart")
  public List<@Valid PlaceOrderRequestItemCartInner> getItemCart() {
    return itemCart;
  }

  public void setItemCart(List<@Valid PlaceOrderRequestItemCartInner> itemCart) {
    this.itemCart = itemCart;
  }

  public PlaceOrderRequest location(String location) {
    this.location = location;
    return this;
  }

  /**
   * Delivery location.
   * @return location
  */
  @NotNull 
  @Schema(name = "location", description = "Delivery location.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("location")
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlaceOrderRequest placeOrderRequest = (PlaceOrderRequest) o;
    return Objects.equals(this.itemCart, placeOrderRequest.itemCart) &&
        Objects.equals(this.location, placeOrderRequest.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemCart, location);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PlaceOrderRequest {\n");
    sb.append("    itemCart: ").append(toIndentedString(itemCart)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
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

