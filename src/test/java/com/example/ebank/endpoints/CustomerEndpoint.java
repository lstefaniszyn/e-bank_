package com.example.ebank.endpoints;

import com.capgemini.mrchecker.test.core.BaseTest;
import com.capgemini.mrchecker.webapi.core.BasePageWebAPI;
import com.capgemini.mrchecker.webapi.core.base.driver.DriverManager;

import io.restassured.response.Response;

public class CustomerEndpoint extends BasePageWebAPI {


    private static final String HOST_ENV_PROPERTY = "HOST";
    private static final String RESOURCE = "/api/v1/customers/";
    private final String endpoint = BaseTest.getEnvironmentService().getValue(HOST_ENV_PROPERTY) + RESOURCE;

    public Response sendGetQuery() {
        return DriverManager.getDriverWebAPI().when().get(endpoint);
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

}
