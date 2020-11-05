package com.example.ebank.integration.endpoints;

import com.capgemini.mrchecker.test.core.BaseTest;

import org.springframework.stereotype.Component;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

@Component
public class CustomerEndpoint {


    private static final String HOST_ENV_PROPERTY = "HOST";
    private static final String RESOURCE = "/api/v1/customers/";
    private final String endpoint = BaseTest.getEnvironmentService().getValue(HOST_ENV_PROPERTY) + RESOURCE;

    public Response sendGetQuery() {
        return SerenityRest.when().get(endpoint);
    }

    public String getEndpoint() {
        return endpoint;
    }

}
