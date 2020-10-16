package com.example.ebank.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.example.ebank.models.Account;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Client
 */

public class Client {
  @JsonProperty("id")
  private Long id = null;
  
  @JsonProperty("givenName")
  private String givenName = null;
  
  @JsonProperty("familyName")
  private String familyName = null;
  
  @JsonProperty("accounts")
  @Valid
  private List<Account> accounts = null;
  
  public Client id(Long id) {
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
  
  public Client givenName(String givenName) {
    this.givenName = givenName;
    return this;
  }
  
  /**
   * https://schema.org/givenName
   * 
   * @return givenName
   **/
  @ApiModelProperty(value = "https://schema.org/givenName")
  
  public String getGivenName() {
    return givenName;
  }
  
  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }
  
  public Client familyName(String familyName) {
    this.familyName = familyName;
    return this;
  }
  
  /**
   * https://schema.org/familyName
   * 
   * @return familyName
   **/
  @ApiModelProperty(value = "https://schema.org/familyName")
  
  public String getFamilyName() {
    return familyName;
  }
  
  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }
  
  public Client accounts(List<Account> accounts) {
    this.accounts = accounts;
    return this;
  }
  
  public Client addAccountsItem(Account accountsItem) {
    if (this.accounts == null) {
      this.accounts = new ArrayList<Account>();
    }
    this.accounts.add(accountsItem);
    return this;
  }
  
  /**
   * Get accounts
   * 
   * @return accounts
   **/
  @ApiModelProperty(value = "")
  @Valid
  public List<Account> getAccounts() {
    return accounts;
  }
  
  public void setAccounts(List<Account> accounts) {
    this.accounts = accounts;
  }
  
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Client client = (Client) o;
    return Objects.equals(this.id, client.id) && Objects.equals(this.givenName, client.givenName)
        && Objects.equals(this.familyName, client.familyName) && Objects.equals(this.accounts, client.accounts);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(id, givenName, familyName, accounts);
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Client {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    givenName: ").append(toIndentedString(givenName)).append("\n");
    sb.append("    familyName: ").append(toIndentedString(familyName)).append("\n");
    sb.append("    accounts: ").append(toIndentedString(accounts)).append("\n");
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
