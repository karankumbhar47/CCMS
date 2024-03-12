package com.example.openapi.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Filter
 */

@JsonTypeName("filter")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-12T04:08:19.693566868+05:30[Asia/Kolkata]")
public class Filter {

  /**
   * What property to apply filter on.
   */
  public enum PropertyEnum {
    PRICE("price"),
    
    VEG("veg"),
    
    RATING("rating"),
    
    VENDOR("vendor"),
    
    CATEGORY("category");

    private String value;

    PropertyEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static PropertyEnum fromValue(String value) {
      for (PropertyEnum b : PropertyEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private PropertyEnum property;

  /**
   * Gets or Sets constrainType
   */
  public enum ConstrainTypeEnum {
    GREATERTHAN("greaterThan"),
    
    LESSTHAN("lessThan"),
    
    EQUALS("equals"),
    
    NOTEQUALS("notEquals");

    private String value;

    ConstrainTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ConstrainTypeEnum fromValue(String value) {
      for (ConstrainTypeEnum b : ConstrainTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private ConstrainTypeEnum constrainType;

  private String value;

  public Filter() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Filter(PropertyEnum property, ConstrainTypeEnum constrainType, String value) {
    this.property = property;
    this.constrainType = constrainType;
    this.value = value;
  }

  public Filter property(PropertyEnum property) {
    this.property = property;
    return this;
  }

  /**
   * What property to apply filter on.
   * @return property
  */
  @NotNull 
  @Schema(name = "property", description = "What property to apply filter on.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("property")
  public PropertyEnum getProperty() {
    return property;
  }

  public void setProperty(PropertyEnum property) {
    this.property = property;
  }

  public Filter constrainType(ConstrainTypeEnum constrainType) {
    this.constrainType = constrainType;
    return this;
  }

  /**
   * Get constrainType
   * @return constrainType
  */
  @NotNull 
  @Schema(name = "constrainType", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("constrainType")
  public ConstrainTypeEnum getConstrainType() {
    return constrainType;
  }

  public void setConstrainType(ConstrainTypeEnum constrainType) {
    this.constrainType = constrainType;
  }

  public Filter value(String value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
  */
  @NotNull 
  @Schema(name = "value", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("value")
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Filter filter = (Filter) o;
    return Objects.equals(this.property, filter.property) &&
        Objects.equals(this.constrainType, filter.constrainType) &&
        Objects.equals(this.value, filter.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(property, constrainType, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Filter {\n");
    sb.append("    property: ").append(toIndentedString(property)).append("\n");
    sb.append("    constrainType: ").append(toIndentedString(constrainType)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

