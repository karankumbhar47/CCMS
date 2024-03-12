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
 * VendorChangeProductAvailabileRequest
 */

@JsonTypeName("vendorChangeProductAvailabile_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-12T16:44:30.158452179+05:30[Asia/Kolkata]")
public class VendorChangeProductAvailabileRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String itemId;

  private Boolean status;

  public VendorChangeProductAvailabileRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public VendorChangeProductAvailabileRequest(String itemId, Boolean status) {
    this.itemId = itemId;
    this.status = status;
  }

  public VendorChangeProductAvailabileRequest itemId(String itemId) {
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

  public VendorChangeProductAvailabileRequest status(Boolean status) {
    this.status = status;
    return this;
  }

  /**
   * Weather the item is available or not
   * @return status
  */
  @NotNull 
  @Schema(name = "status", description = "Weather the item is available or not", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VendorChangeProductAvailabileRequest vendorChangeProductAvailabileRequest = (VendorChangeProductAvailabileRequest) o;
    return Objects.equals(this.itemId, vendorChangeProductAvailabileRequest.itemId) &&
        Objects.equals(this.status, vendorChangeProductAvailabileRequest.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VendorChangeProductAvailabileRequest {\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

