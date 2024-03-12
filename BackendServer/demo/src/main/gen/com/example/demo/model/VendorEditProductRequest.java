package com.example.demo.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * VendorEditProductRequest
 */

@JsonTypeName("vendorEditProduct_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-12T16:44:30.158452179+05:30[Asia/Kolkata]")
public class VendorEditProductRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String itemId;

  private String name;

  private String thumbnail;

  private BigDecimal price;

  private Integer maxQuantity;

  public VendorEditProductRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public VendorEditProductRequest(String itemId) {
    this.itemId = itemId;
  }

  public VendorEditProductRequest itemId(String itemId) {
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

  public VendorEditProductRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public VendorEditProductRequest thumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
    return this;
  }

  /**
   * Get thumbnail
   * @return thumbnail
  */
  
  @Schema(name = "thumbnail", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("thumbnail")
  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public VendorEditProductRequest price(BigDecimal price) {
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

  public VendorEditProductRequest maxQuantity(Integer maxQuantity) {
    this.maxQuantity = maxQuantity;
    return this;
  }

  /**
   * Get maxQuantity
   * @return maxQuantity
  */
  
  @Schema(name = "maxQuantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("maxQuantity")
  public Integer getMaxQuantity() {
    return maxQuantity;
  }

  public void setMaxQuantity(Integer maxQuantity) {
    this.maxQuantity = maxQuantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VendorEditProductRequest vendorEditProductRequest = (VendorEditProductRequest) o;
    return Objects.equals(this.itemId, vendorEditProductRequest.itemId) &&
        Objects.equals(this.name, vendorEditProductRequest.name) &&
        Objects.equals(this.thumbnail, vendorEditProductRequest.thumbnail) &&
        Objects.equals(this.price, vendorEditProductRequest.price) &&
        Objects.equals(this.maxQuantity, vendorEditProductRequest.maxQuantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, name, thumbnail, price, maxQuantity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VendorEditProductRequest {\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    thumbnail: ").append(toIndentedString(thumbnail)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    maxQuantity: ").append(toIndentedString(maxQuantity)).append("\n");
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

