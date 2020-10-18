package com.example.ebank.models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class AppStatus {
    @JsonProperty("app-version")
  private String appVersion = null;

  public AppStatus appVersion(String appVersion) {
    this.appVersion = appVersion;
    return this;
  }

  /**
   * https://schema.org/version
   * @return appVersion
  **/
  @ApiModelProperty(value = "https://schema.org/version")
  
    public String getAppVersion() {
    return appVersion;
  }

  public void setAppVersion(String appVersion) {
    this.appVersion = appVersion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppStatus appStatus = (AppStatus) o;
    return Objects.equals(this.appVersion, appStatus.appVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppStatus {\n");
    
    sb.append("    appVersion: ").append(toIndentedString(appVersion)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

}
