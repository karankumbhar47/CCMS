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
 * FoodItem
 */

@JsonTypeName("foodItem")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-12T16:44:30.158452179+05:30[Asia/Kolkata]")
public class FoodItem implements Serializable {

  private static final long serialVersionUID = 1L;

  private String itemId;

  private String name;

  private String thumbnail;

  private String vendor;

  private BigDecimal price;

  private BigDecimal rating;

  private Integer ratedBy;

  @Valid
  private List<String> tags;

  public FoodItem() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public FoodItem(String itemId, String name, String vendor, BigDecimal price) {
    this.itemId = itemId;
    this.name = name;
    this.vendor = vendor;
    this.price = price;
  }

  public FoodItem itemId(String itemId) {
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

  public FoodItem name(String name) {
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

  public FoodItem thumbnail(String thumbnail) {
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

  public FoodItem vendor(String vendor) {
    this.vendor = vendor;
    return this;
  }

  /**
   * Get vendor
   * @return vendor
  */
  @NotNull 
  @Schema(name = "vendor", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("vendor")
  public String getVendor() {
    return vendor;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  public FoodItem price(BigDecimal price) {
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

  public FoodItem rating(BigDecimal rating) {
    this.rating = rating;
    return this;
  }

  /**
   * Get rating
   * @return rating
  */
  @Valid 
  @Schema(name = "rating", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("rating")
  public BigDecimal getRating() {
    return rating;
  }

  public void setRating(BigDecimal rating) {
    this.rating = rating;
  }

  public FoodItem ratedBy(Integer ratedBy) {
    this.ratedBy = ratedBy;
    return this;
  }

  /**
   * Get ratedBy
   * @return ratedBy
  */
  
  @Schema(name = "ratedBy", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ratedBy")
  public Integer getRatedBy() {
    return ratedBy;
  }

  public void setRatedBy(Integer ratedBy) {
    this.ratedBy = ratedBy;
  }

  public FoodItem tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public FoodItem addTagsItem(String tagsItem) {
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
    FoodItem foodItem = (FoodItem) o;
    return Objects.equals(this.itemId, foodItem.itemId) &&
        Objects.equals(this.name, foodItem.name) &&
        Objects.equals(this.thumbnail, foodItem.thumbnail) &&
        Objects.equals(this.vendor, foodItem.vendor) &&
        Objects.equals(this.price, foodItem.price) &&
        Objects.equals(this.rating, foodItem.rating) &&
        Objects.equals(this.ratedBy, foodItem.ratedBy) &&
        Objects.equals(this.tags, foodItem.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, name, thumbnail, vendor, price, rating, ratedBy, tags);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FoodItem {\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    thumbnail: ").append(toIndentedString(thumbnail)).append("\n");
    sb.append("    vendor: ").append(toIndentedString(vendor)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
    sb.append("    ratedBy: ").append(toIndentedString(ratedBy)).append("\n");
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

