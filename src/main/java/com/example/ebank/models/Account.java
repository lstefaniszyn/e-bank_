package com.example.ebank.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.example.ebank.models.Currency;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Account
 */

public class Account {
  @JsonProperty("id")
  private Long id = null;
  
  @JsonProperty("name")
  private String name = null;
  
  @JsonProperty("currency")
  private Currency currency = null;
  
  public Account id(Long id) {
    this.id = id;
    return this;
  }
  
  /**
   * Get id
   * 
   * @return id
   **/
  @ApiModelProperty(value = "")
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Account name(String name) {
    this.name = name;
    return this;
  }
  
  /**
   * Get name
   * 
   * @return name
   **/
  @ApiModelProperty(value = "")
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public Account currency(Currency currency) {
    this.currency = currency;
    return this;
  }
  
  /**
   * Get currency
   * 
   * @return currency
   **/
  @ApiModelProperty(value = "")
  
  @Valid
  public Currency getCurrency() {
    return currency;
  }
  
  public void setCurrency(Currency currency) {
    this.currency = currency;
  }
  
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return Objects.equals(this.id, account.id) && Objects.equals(this.name, account.name)
        && Objects.equals(this.currency, account.currency);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(id, name, currency);
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Account {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
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
