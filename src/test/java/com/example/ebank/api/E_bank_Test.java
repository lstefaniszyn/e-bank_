package com.example.ebank.api;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.capgemini.mrchecker.test.core.logger.BFLogger;
import com.example.ebank.controllers.AppStatusController;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.junit.Before;
import org.junit.Test;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.springframework.boot.web.server.LocalServerPort;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;

public class E_bank_Test {
    
    // @InjectMocks
    // AppStatusController appStatusController;
    
    @Before
    public void setup() {
        BFLogger.logDebug("!!!! TEST ME !!!");
        RestAssuredMockMvc.standaloneSetup(new AppStatusController());
    }
    
    @Test
    public void validate_appStatus_OK_Response() throws Exception {
        // given:
        MockMvcRequestSpecification request = given();
        
        // when:
        ResponseOptions response = given().spec(request)
                .get("/api");
        
        // then:
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.header("Content-Type")).isEqualTo("application/json;charset=UTF-8");
        // and:
        DocumentContext parsedJson = JsonPath.parse(response.getBody()
                .asString());
        // and:
        assertThat(parsedJson.read("$.['app-version']", String.class)).matches("[0-9]+\\.[0-9]+\\.[0-9]+");
    }
    
}
