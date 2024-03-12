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
 * FoodItemFull
 */

@JsonTypeName("foodItemFull")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-12T16:44:30.158452179+05:30[Asia/Kolkata]")
public class FoodItemFull implements Serializable {

  private static final long serialVersionUID = 1L;

  private String itemId;

  private String itemName;

  private String thumbnailPicture;

  private BigDecimal price;

  private String vendorName;

  private String vendorLocation;

  private BigDecimal starRating;

  private Boolean isAvailable;

  private Integer maxQuantity;

  @Valid
  private List<String> imageUrls;

  @Valid
  private List<String> tags;

  public FoodItemFull itemId(String itemId) {
    this.itemId = itemId;
    return this;
  }

  /**
   * ID of the item
   * @return itemId
  */
  
  @Schema(name = "itemId", description = "ID of the item", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("itemId")
  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public FoodItemFull itemName(String itemName) {
    this.itemName = itemName;
    return this;
  }

  /**
   * Name of the item
   * @return itemName
  */
  
  @Schema(name = "itemName", description = "Name of the item", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("itemName")
  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public FoodItemFull thumbnailPicture(String thumbnailPicture) {
    this.thumbnailPicture = thumbnailPicture;
    return this;
  }

  /**
   * 64bit encoded thumbnail picture
   * @return thumbnailPicture
  */
  
  @Schema(name = "thumbnailPicture", description = "64bit encoded thumbnail picture", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("thumbnailPicture")
  public String getThumbnailPicture() {
    return thumbnailPicture;
  }

  public void setThumbnailPicture(String thumbnailPicture) {
    this.thumbnailPicture = thumbnailPicture;
  }

  public FoodItemFull price(BigDecimal price) {
    this.price = price;
    return this;
  }

  /**
   * Price of the item
   * @return price
  */
  @Valid 
  @Schema(name = "price", description = "Price of the item", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("price")
  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public FoodItemFull vendorName(String vendorName) {
    this.vendorName = vendorName;
    return this;
  }

  /**
   * Name of the vendor
   * @return vendorName
  */
  
  @Schema(name = "vendorName", description = "Name of the vendor", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("vendorName")
  public String getVendorName() {
    return vendorName;
  }

  public void setVendorName(String vendorName) {
    this.vendorName = vendorName;
  }

  public FoodItemFull vendorLocation(String vendorLocation) {
    this.vendorLocation = vendorLocation;
    return this;
  }

  /**
   * Location of the vendor
   * @return vendorLocation
  */
  
  @Schema(name = "vendorLocation", description = "Location of the vendor", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("vendorLocation")
  public String getVendorLocation() {
    return vendorLocation;
  }

  public void setVendorLocation(String vendorLocation) {
    this.vendorLocation = vendorLocation;
  }

  public FoodItemFull starRating(BigDecimal starRating) {
    this.starRating = starRating;
    return this;
  }

  /**
   * Star rating of the item
   * @return starRating
  */
  @Valid 
  @Schema(name = "starRating", description = "Star rating of the item", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("starRating")
  public BigDecimal getStarRating() {
    return starRating;
  }

  public void setStarRating(BigDecimal starRating) {
    this.starRating = starRating;
  }

  public FoodItemFull isAvailable(Boolean isAvailable) {
    this.isAvailable = isAvailable;
    return this;
  }

  /**
   * Indicates if the item is available
   * @return isAvailable
  */
  
  @Schema(name = "isAvailable", description = "Indicates if the item is available", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isAvailable")
  public Boolean getIsAvailable() {
    return isAvailable;
  }

  public void setIsAvailable(Boolean isAvailable) {
    this.isAvailable = isAvailable;
  }

  public FoodItemFull maxQuantity(Integer maxQuantity) {
    this.maxQuantity = maxQuantity;
    return this;
  }

  /**
   * Maximum quantity available
   * @return maxQuantity
  */
  
  @Schema(name = "maxQuantity", description = "Maximum quantity available", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("maxQuantity")
  public Integer getMaxQuantity() {
    return maxQuantity;
  }

  public void setMaxQuantity(Integer maxQuantity) {
    this.maxQuantity = maxQuantity;
  }

  public FoodItemFull imageUrls(List<String> imageUrls) {
    this.imageUrls = imageUrls;
    return this;
  }

  public FoodItemFull addImageUrlsItem(String imageUrlsItem) {
    if (this.imageUrls == null) {
      this.imageUrls = new ArrayList<>();
    }
    this.imageUrls.add(imageUrlsItem);
    return this;
  }

  /**
   * URLs of additional images
   * @return imageUrls
  */
  
  @Schema(name = "imageUrls", description = "URLs of additional images", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("imageUrls")
  public List<String> getImageUrls() {
    return imageUrls;
  }

  public void setImageUrls(List<String> imageUrls) {
    this.imageUrls = imageUrls;
  }

  public FoodItemFull tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public FoodItemFull addTagsItem(String tagsItem) {
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
    FoodItemFull foodItemFull = (FoodItemFull) o;
    return Objects.equals(this.itemId, foodItemFull.itemId) &&
        Objects.equals(this.itemName, foodItemFull.itemName) &&
        Objects.equals(this.thumbnailPicture, foodItemFull.thumbnailPicture) &&
        Objects.equals(this.price, foodItemFull.price) &&
        Objects.equals(this.vendorName, foodItemFull.vendorName) &&
        Objects.equals(this.vendorLocation, foodItemFull.vendorLocation) &&
        Objects.equals(this.starRating, foodItemFull.starRating) &&
        Objects.equals(this.isAvailable, foodItemFull.isAvailable) &&
        Objects.equals(this.maxQuantity, foodItemFull.maxQuantity) &&
        Objects.equals(this.imageUrls, foodItemFull.imageUrls) &&
        Objects.equals(this.tags, foodItemFull.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, itemName, thumbnailPicture, price, vendorName, vendorLocation, starRating, isAvailable, maxQuantity, imageUrls, tags);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FoodItemFull {\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    thumbnailPicture: ").append(toIndentedString(thumbnailPicture)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    vendorName: ").append(toIndentedString(vendorName)).append("\n");
    sb.append("    vendorLocation: ").append(toIndentedString(vendorLocation)).append("\n");
    sb.append("    starRating: ").append(toIndentedString(starRating)).append("\n");
    sb.append("    isAvailable: ").append(toIndentedString(isAvailable)).append("\n");
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

