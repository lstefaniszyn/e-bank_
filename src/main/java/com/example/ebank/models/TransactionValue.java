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
 * TransactionValue
 */

public class TransactionValue {
  @JsonProperty("amount")
  private Long amount = null;
  
  @JsonProperty("currency")
  private Currency currency = null;
  
  public TransactionValue amount(Long amount) {
    this.amount = amount;
    return this;
  }
  
  /**
   * Get amount
   * 
   * @return amount
   **/
  @ApiModelProperty(value = "")
  
  public Long getAmount() {
    return amount;
  }
  
  public void setAmount(Long amount) {
    this.amount = amount;
  }
  
  public TransactionValue currency(Currency currency) {
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
    TransactionValue transactionValue = (TransactionValue) o;
    return Objects.equals(this.amount, transactionValue.amount)
        && Objects.equals(this.currency, transactionValue.currency);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(amount, currency);
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionValue {\n");
    
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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
