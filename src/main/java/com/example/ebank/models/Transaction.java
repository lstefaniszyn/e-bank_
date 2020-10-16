package com.example.ebank.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.example.ebank.models.TransactionValue;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Transaction
 */

public class Transaction {
  @JsonProperty("id")
  private Long id = null;
  
  @JsonProperty("value")
  private TransactionValue value = null;
  
  @JsonProperty("iban")
  private String iban = null;
  
  @JsonProperty("date")
  private OffsetDateTime date = null;
  
  @JsonProperty("description")
  private String description = null;
  
  public Transaction id(Long id) {
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
  
  public Transaction value(TransactionValue value) {
    this.value = value;
    return this;
  }
  
  /**
   * Get value
   * 
   * @return value
   **/
  @ApiModelProperty(value = "")
  
  @Valid
  public TransactionValue getValue() {
    return value;
  }
  
  public void setValue(TransactionValue value) {
    this.value = value;
  }
  
  public Transaction iban(String iban) {
    this.iban = iban;
    return this;
  }
  
  /**
   * Get iban
   * 
   * @return iban
   **/
  @ApiModelProperty(value = "")
  
  public String getIban() {
    return iban;
  }
  
  public void setIban(String iban) {
    this.iban = iban;
  }
  
  public Transaction date(OffsetDateTime date) {
    this.date = date;
    return this;
  }
  
  /**
   * Get date
   * 
   * @return date
   **/
  @ApiModelProperty(value = "")
  
  @Valid
  public OffsetDateTime getDate() {
    return date;
  }
  
  public void setDate(OffsetDateTime date) {
    this.date = date;
  }
  
  public Transaction description(String description) {
    this.description = description;
    return this;
  }
  
  /**
   * Get description
   * 
   * @return description
   **/
  @ApiModelProperty(value = "")
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction transaction = (Transaction) o;
    return Objects.equals(this.id, transaction.id) && Objects.equals(this.value, transaction.value)
        && Objects.equals(this.iban, transaction.iban) && Objects.equals(this.date, transaction.date)
        && Objects.equals(this.description, transaction.description);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(id, value, iban, date, description);
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    iban: ").append(toIndentedString(iban)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
