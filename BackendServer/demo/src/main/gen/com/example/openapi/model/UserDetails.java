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
 * UserDetails
 */

@JsonTypeName("userDetails")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-12T04:08:19.693566868+05:30[Asia/Kolkata]")
public class UserDetails {

  private String username;

  private String password;

  private String name;

  private String phone;

  private String email;

  /**
   * Gets or Sets gender
   */
  public enum GenderEnum {
    MALE("Male"),
    
    FEMALE("Female"),
    
    OTHERS("Others");

    private String value;

    GenderEnum(String value) {
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
    public static GenderEnum fromValue(String value) {
      for (GenderEnum b : GenderEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private GenderEnum gender;

  private String dob;

  public UserDetails() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UserDetails(String username, String password, String phone) {
    this.username = username;
    this.password = password;
    this.phone = phone;
  }

  public UserDetails username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
  */
  @NotNull 
  @Schema(name = "username", example = "testUser1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public UserDetails password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
  */
  @NotNull 
  @Schema(name = "password", example = "1234@Abcd", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserDetails name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  
  @Schema(name = "name", example = "Test D. User", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UserDetails phone(String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * Get phone
   * @return phone
  */
  @NotNull 
  @Schema(name = "phone", example = "9876543210", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("phone")
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public UserDetails email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
  */
  
  @Schema(name = "email", example = "email@example.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserDetails gender(GenderEnum gender) {
    this.gender = gender;
    return this;
  }

  /**
   * Get gender
   * @return gender
  */
  
  @Schema(name = "gender", example = "Male", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("gender")
  public GenderEnum getGender() {
    return gender;
  }

  public void setGender(GenderEnum gender) {
    this.gender = gender;
  }

  public UserDetails dob(String dob) {
    this.dob = dob;
    return this;
  }

  /**
   * Get dob
   * @return dob
  */
  
  @Schema(name = "dob", example = "01-01-2000", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dob")
  public String getDob() {
    return dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDetails userDetails = (UserDetails) o;
    return Objects.equals(this.username, userDetails.username) &&
        Objects.equals(this.password, userDetails.password) &&
        Objects.equals(this.name, userDetails.name) &&
        Objects.equals(this.phone, userDetails.phone) &&
        Objects.equals(this.email, userDetails.email) &&
        Objects.equals(this.gender, userDetails.gender) &&
        Objects.equals(this.dob, userDetails.dob);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password, name, phone, email, gender, dob);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserDetails {\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    dob: ").append(toIndentedString(dob)).append("\n");
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

