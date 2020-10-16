package com.example.ebank.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Currency
 */

public class Currency {
  @JsonProperty("name")
  private String name = null;
  
  @JsonProperty("code")
  private String code = null;
  
  public Currency name(String name) {
    this.name = name;
    return this;
  }
  
  /**
   * like, Euro
   * 
   * @return name
   **/
  @ApiModelProperty(value = "like, Euro")
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public Currency code(String code) {
    this.code = code;
    return this;
  }
  
  /**
   * like, EUR
   * 
   * @return code
   **/
  @ApiModelProperty(value = "like, EUR")
  
  public String getCode() {
    return code;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Currency currency = (Currency) o;
    return Objects.equals(this.name, currency.name) && Objects.equals(this.code, currency.code);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(name, code);
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Currency {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("}");
    return sb.toString();
  }
  
  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
