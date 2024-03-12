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
 * VendorDetails
 */

@JsonTypeName("vendorDetails")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-12T16:44:30.158452179+05:30[Asia/Kolkata]")
public class VendorDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  private String username;

  private String password;

  private String name;

  private String phone;

  private String email;

  private String location;

  public VendorDetails() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public VendorDetails(String username, String password, String phone, String location) {
    this.username = username;
    this.password = password;
    this.phone = phone;
    this.location = location;
  }

  public VendorDetails username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
  */
  @NotNull 
  @Schema(name = "username", example = "testVendor1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public VendorDetails password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
  */
  @NotNull 
  @Schema(name = "password", example = "1234@Ved", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public VendorDetails name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  
  @Schema(name = "name", example = "UltraTech Cafe", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public VendorDetails phone(String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * Get phone
   * @return phone
  */
  @NotNull 
  @Schema(name = "phone", example = "9878543210", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("phone")
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public VendorDetails email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
  */
  
  @Schema(name = "email", example = "vendor001@example.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public VendorDetails location(String location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   * @return location
  */
  @NotNull 
  @Schema(name = "location", example = "Mess Block", requiredMode = Schema.RequiredMode.REQUIRED)
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
    VendorDetails vendorDetails = (VendorDetails) o;
    return Objects.equals(this.username, vendorDetails.username) &&
        Objects.equals(this.password, vendorDetails.password) &&
        Objects.equals(this.name, vendorDetails.name) &&
        Objects.equals(this.phone, vendorDetails.phone) &&
        Objects.equals(this.email, vendorDetails.email) &&
        Objects.equals(this.location, vendorDetails.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password, name, phone, email, location);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VendorDetails {\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
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

