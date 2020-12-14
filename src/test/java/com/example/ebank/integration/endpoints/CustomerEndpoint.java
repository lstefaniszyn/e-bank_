package com.example.ebank.integration.endpoints;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

@Component
public class CustomerEndpoint {
    
    private static final String RESOURCE = "/api";
    private final String endpoint;
    
    public CustomerEndpoint(@Value("${it.host}") String hostToTest) {
        endpoint = hostToTest + RESOURCE;
    }
    
    public Response sendGetQuery() {
        return SerenityRest.when()
                .get(endpoint);
    }
    
    public String getEndpoint() {
        return endpoint;
    }
    
}
