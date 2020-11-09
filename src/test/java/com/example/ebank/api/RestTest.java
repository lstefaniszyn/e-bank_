package com.example.ebank.api;

import com.example.ebank.api.RestBase;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.Rule;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;

import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;
import static org.springframework.cloud.contract.verifier.util.ContractVerifierUtil.*;
import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

@SuppressWarnings("rawtypes")
public class RestTest extends RestBase {
    
    @Test
    public void validate_get_account_OK_Response() throws Exception {
        // given:
        MockMvcRequestSpecification request = given()
                .header("Content-Type", "application/json");
        
        // when:
        ResponseOptions response = given().spec(request)
                .get("api/v1/customers/1/accounts/1");
        
        // then:
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.header("Content-Type")).matches("application/json.*");
        
        // and:
        DocumentContext parsedJson = JsonPath.parse(response.getBody()
                .asString());
        assertThatJson(parsedJson).field("['currency.code']")
                .isEqualTo("PLN");
        assertThatJson(parsedJson).field("['balance.value']")
                .isEqualTo(123.12);
        assertThatJson(parsedJson).field("['balance.currency.code']")
                .isEqualTo("PLN");
        
        // and:
        assertThat(parsedJson.read("id", String.class)).matches("-?(\\d*\\.\\d+|\\d+)");
        assertThat(parsedJson.read("name", String.class)).matches(".+");
        assertThat(parsedJson.read("iban", String.class)).matches("\\w\\w[\\d]{16,24}");
        assertThat(parsedJson.read("currency.code", String.class)).matches("[A-Z]{3}");
        assertThat(parsedJson.read("balance.value", String.class)).matches("-?(\\d*\\.\\d+)");
        assertThat(parsedJson.read("balance.currency.code", String.class)).matches("[A-Z]{3}");
    }
    
    @Test
    public void validate_get_accounts_OK_Response() throws Exception {
        // given:
        MockMvcRequestSpecification request = given()
                .header("Content-Type", "application/json");
        
        // when:
        ResponseOptions response = given().spec(request)
                .get("api/v1/customers/1/accounts");
        
        // then:
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.header("Content-Type")).matches("application/json.*");
        
        // and:
        DocumentContext parsedJson = JsonPath.parse(response.getBody()
                .asString());
        assertThatJson(parsedJson).array()
                .contains("['id']")
                .isEqualTo(1);
        assertThatJson(parsedJson).array()
                .contains("['name']")
                .isEqualTo("For fun account");
        assertThatJson(parsedJson).array()
                .contains("['iban']")
                .isEqualTo("PL10105000997603123456789123");
        assertThatJson(parsedJson).array()
                .contains("['currency.code']")
                .isEqualTo("PLN");
        assertThatJson(parsedJson).array()
                .contains("['balance.value']")
                .isEqualTo(123.12);
        assertThatJson(parsedJson).array()
                .contains("['balance.currency.code']")
                .isEqualTo("PLN");
        
        // and:
        assertThat((Object) parsedJson.read("$")).isInstanceOf(java.util.List.class);
        assertThat((java.lang.Iterable) parsedJson.read("$", java.util.Collection.class)).as("$")
                .hasSizeGreaterThanOrEqualTo(1);
    }
    
    @Test
    public void validate_get_AppStatus_OK_Response() throws Exception {
        // given:
        MockMvcRequestSpecification request = given()
                .header("Content-Type", "application/json");
        
        // when:
        ResponseOptions response = given().spec(request)
                .get("/api");
        
        // then:
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.header("Content-Type")).matches("application/json.*");
        
        // and:
        DocumentContext parsedJson = JsonPath.parse(response.getBody()
                .asString());
        assertThatJson(parsedJson).field("['app-version']")
                .matches("[0-9]+\\.[0-9]+\\.[0-9]+");
    }
    
    @Test
    public void validate_get_consumer_OK_Response() throws Exception {
        // given:
        MockMvcRequestSpecification request = given()
                .header("Content-Type", "application/json");
        
        // when:
        ResponseOptions response = given().spec(request)
                .get("api/v1/customers/1");
        
        // then:
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.header("Content-Type")).matches("application/json.*");
        
        // and:
        DocumentContext parsedJson = JsonPath.parse(response.getBody()
                .asString());
        assertThatJson(parsedJson).field("['balance.value']")
                .isEqualTo(123.12);
        assertThatJson(parsedJson).field("['balance.currency.code']")
                .isEqualTo("EUR");
        
        // and:
        assertThat(parsedJson.read("id", String.class)).matches("-?(\\d*\\.\\d+|\\d+)");
        assertThat(parsedJson.read("givenName", String.class)).matches("[\\w-_\\s\\.]+");
        assertThat(parsedJson.read("familyName", String.class)).matches("[\\w-_\\s\\.]+");
        assertThat(parsedJson.read("identityKey", String.class)).matches("-?(\\d*\\.\\d+|\\d+)");
        assertThat((Object) parsedJson.read("accounts")).isInstanceOf(java.util.List.class);
        assertThat((java.lang.Iterable) parsedJson.read("accounts", java.util.Collection.class)).as("accounts")
                .hasSizeGreaterThanOrEqualTo(1);
        assertThat(parsedJson.read("balance.value", String.class)).matches("-?(\\d*\\.\\d+)");
        assertThat(parsedJson.read("balance.currency.code", String.class)).matches("[A-Z]{3}");
    }
    
    @Test
    public void validate_get_consumers_OK_Response() throws Exception {
        // given:
        MockMvcRequestSpecification request = given()
                .header("Content-Type", "application/json");
        
        // when:
        ResponseOptions response = given().spec(request)
                .get("/api/v1/customers");
        
        // then:
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.header("Content-Type")).matches("application/json.*");
        
        // and:
        DocumentContext parsedJson = JsonPath.parse(response.getBody()
                .asString());
        assertThatJson(parsedJson).array()
                .contains("['id']")
                .isEqualTo(1);
        assertThatJson(parsedJson).array()
                .contains("['givenName']")
                .isEqualTo("Johnny");
        assertThatJson(parsedJson).array()
                .contains("['familyName']")
                .isEqualTo("Bravo");
        assertThatJson(parsedJson).array()
                .contains("['id']")
                .isEqualTo(2);
        assertThatJson(parsedJson).array()
                .contains("['givenName']")
                .isEqualTo("Audrey");
        assertThatJson(parsedJson).array()
                .contains("['familyName']")
                .isEqualTo("Hepburn");
        
        // and:
        assertThat((Object) parsedJson.read("$")).isInstanceOf(java.util.List.class);
        assertThat((java.lang.Iterable) parsedJson.read("$", java.util.Collection.class)).as("$")
                .hasSizeGreaterThanOrEqualTo(1);
    }
    
    @Test
    public void validate_get_transactions_OK_Response() throws Exception {
        // given:
        MockMvcRequestSpecification request = given()
                .header("Content-Type", "application/json");
        
        // when:
        ResponseOptions response = given().spec(request)
                .queryParam("date", "2019-01")
                .queryParam("page", "0")
                .queryParam("size", "2")
                .get("api/v1/customers/1/accounts/1/transactions");
        
        // then:
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.header("Content-Type")).matches("application/json.*");
        
        // and:
        DocumentContext parsedJson = JsonPath.parse(response.getBody()
                .asString());
        assertThatJson(parsedJson).array("['content']")
                .contains("['id']")
                .isEqualTo(1);
        assertThatJson(parsedJson).array("['content']")
                .contains("['value.amount']")
                .isEqualTo(123.12);
        assertThatJson(parsedJson).array("['content']")
                .contains("['value.currency.code']")
                .isEqualTo("EUR");
        assertThatJson(parsedJson).array("['content']")
                .contains("['id']")
                .isEqualTo(2);
        assertThatJson(parsedJson).array("['content']")
                .contains("['value.amount']")
                .isEqualTo(1111.1);
        assertThatJson(parsedJson).array("['content']")
                .contains("['iban']")
                .isEqualTo("GB33BUKB20201555555555");
        assertThatJson(parsedJson).array("['content']")
                .contains("['date']")
                .isEqualTo("2133.01.01 03:29:36 PDT");
        assertThatJson(parsedJson).array("['content']")
                .contains("['description']")
                .isEqualTo("superb*");
        
        // and:
        assertThat(parsedJson.read("page.size", String.class)).matches("-?(\\d*\\.\\d+|\\d+)");
        assertThat(parsedJson.read("page.totalElements", String.class)).matches("-?(\\d*\\.\\d+|\\d+)");
        assertThat(parsedJson.read("page.totalPages", String.class)).matches("-?(\\d*\\.\\d+|\\d+)");
        assertThat(parsedJson.read("page.number", String.class)).matches("-?(\\d*\\.\\d+|\\d+)");
        assertThat(parsedJson.read("$.['content'][0].['value'].['amount']", String.class)).matches("-?(\\d*\\.\\d+)");
        assertThat(parsedJson.read("$.['content'][0].['value'].['currency'].['code']", String.class)).matches("[A-Z]{3}");
        assertThat(parsedJson.read("$.['content'][0].['iban']", String.class)).matches("\\w\\w[\\d]{16,24}");
        assertThat(parsedJson.read("$.['content'][0].['date']", String.class)).matches("\\d{4}-\\d{2}-\\d{2}(T|t)\\d{2}:\\d{2}:\\d{2}[\\w]?");
        assertThat(parsedJson.read("$.['content'][0].['description']", String.class)).matches(".+");
    }
    
}
