package com.example.demo.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * VendorAddProductRequest
 */

@JsonTypeName("vendorAddProduct_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-12T16:44:30.158452179+05:30[Asia/Kolkata]")
public class VendorAddProductRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String name;

  private String thumbnail;

  private BigDecimal price;

  private Integer maxQuantity;

  @Valid
  private List<String> imageUrls;

  @Valid
  private List<String> tags;

  public VendorAddProductRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public VendorAddProductRequest(String name, BigDecimal price) {
    this.name = name;
    this.price = price;
  }

  public VendorAddProductRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @NotNull 
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public VendorAddProductRequest thumbnail(String thumbnail) {
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

  public VendorAddProductRequest price(BigDecimal price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
  */
  @NotNull @Valid 
  @Schema(name = "price", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("price")
  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public VendorAddProductRequest maxQuantity(Integer maxQuantity) {
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

  public VendorAddProductRequest imageUrls(List<String> imageUrls) {
    this.imageUrls = imageUrls;
    return this;
  }

  public VendorAddProductRequest addImageUrlsItem(String imageUrlsItem) {
    if (this.imageUrls == null) {
      this.imageUrls = new ArrayList<>();
    }
    this.imageUrls.add(imageUrlsItem);
    return this;
  }

  /**
   * Get imageUrls
   * @return imageUrls
  */
  
  @Schema(name = "imageUrls", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("imageUrls")
  public List<String> getImageUrls() {
    return imageUrls;
  }

  public void setImageUrls(List<String> imageUrls) {
    this.imageUrls = imageUrls;
  }

  public VendorAddProductRequest tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public VendorAddProductRequest addTagsItem(String tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

  /**
   * tags related to food item
   * @return tags
  */
  
  @Schema(name = "tags", description = "tags related to food item", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tags")
  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VendorAddProductRequest vendorAddProductRequest = (VendorAddProductRequest) o;
    return Objects.equals(this.name, vendorAddProductRequest.name) &&
        Objects.equals(this.thumbnail, vendorAddProductRequest.thumbnail) &&
        Objects.equals(this.price, vendorAddProductRequest.price) &&
        Objects.equals(this.maxQuantity, vendorAddProductRequest.maxQuantity) &&
        Objects.equals(this.imageUrls, vendorAddProductRequest.imageUrls) &&
        Objects.equals(this.tags, vendorAddProductRequest.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, thumbnail, price, maxQuantity, imageUrls, tags);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VendorAddProductRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    thumbnail: ").append(toIndentedString(thumbnail)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    maxQuantity: ").append(toIndentedString(maxQuantity)).append("\n");
    sb.append("    imageUrls: ").append(toIndentedString(imageUrls)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
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

