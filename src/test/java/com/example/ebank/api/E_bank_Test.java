package com.example.ebank.api;

import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.capgemini.mrchecker.test.core.logger.BFLogger;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.junit.Test;

import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;

public class E_bank_Test extends AbstractContractSpec {
    
    @Test
    public void validate_appStatus_OK_Response() throws Exception {
        // given:
        MockMvcRequestSpecification request = given();
        
        // when:
        ResponseOptions response = given().spec(request)
                .get("/api");
        
        // then:
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.header("Content-Type")).isEqualTo("application/json");
        // and:
        DocumentContext parsedJson = JsonPath.parse(response.getBody()
                .asString());
        // and:
        assertThat(parsedJson.read("$.['app-version']", String.class)).matches("[0-9]+\\.[0-9]+\\.[0-9]+");
    }
    
    @Test
    public void validate_get_customers_OK_Response() throws Exception {
        // given:
        MockMvcRequestSpecification request = given();
        
        // when:
        ResponseOptions response = given().spec(request)
                .get("/api/v1/customers");
        
        // then:
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.header("Content-Type")).isEqualTo("application/json");
        // and:
        DocumentContext parsedJson = JsonPath.parse(response.getBody()
                .asString());
        
        BFLogger.logDebug("parsedJson list: " + parsedJson.jsonString());
        BFLogger.logDebug("parsedJson item:" + parsedJson.read("$[0].id"));
        BFLogger.logDebug("parsedJson item:" + parsedJson.read("$[0].givenName"));
        BFLogger.logDebug("parsedJson item:" + parsedJson.read("$[0].familyName"));
        
        // and:
        assertThat(parsedJson.read("$[0].id", String.class)).matches("[\\d]+");
        assertThat(parsedJson.read("$[0].givenName", String.class)).matches("[\\w-_\\.]+");
        assertThat(parsedJson.read("$[0].familyName", String.class)).matches("[\\w-_\\.]+");
    }
    
    @Test
    public void validate_get_one_customer_OK_Response() throws Exception {
        // given:
        MockMvcRequestSpecification request = given();
        
        // when:
        ResponseOptions response = given().spec(request)
                .get("/api/v1/customers/{idCustomer}", 1);
        
        // then:
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.header("Content-Type")).isEqualTo("application/json");
        // and:
        DocumentContext parsedJson = JsonPath.parse(response.getBody()
                .asString());
        BFLogger.logDebug("parsedJson: " + parsedJson.jsonString());
        assertThatJson(parsedJson).field("['id']")
                .isEqualTo("1");
        // and:
        assertThat(parsedJson.read("$.['givenName']", String.class)).matches("[\\w-_\\.]+");
        assertThat(parsedJson.read("$.['familyName']", String.class)).matches("[\\w-_\\.]+");
    }
    
}
