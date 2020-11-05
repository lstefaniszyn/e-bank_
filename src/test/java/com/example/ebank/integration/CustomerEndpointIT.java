package com.example.ebank.integration;

import static org.hamcrest.Matchers.equalTo;

import com.example.ebank.integration.endpoints.CustomerEndpoint;
import com.example.ebank.integration.serenity.SerenityReportBase;
import com.example.ebank.utils.logger.BFLogger;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.serenitybdd.rest.SerenityRest;

public class CustomerEndpointIT extends SerenityReportBase {

    @Autowired
    private CustomerEndpoint endpoint;


    @Test
    public void simpleGetQuery() {

        BFLogger.logInfo("Step 1 - Sending GET query to " + endpoint.getEndpoint());
        SerenityRest.get(endpoint.getEndpoint());

        BFLogger.logInfo("Step 2 - Validate response status code");
        SerenityRest.restAssuredThat(resp -> resp.statusCode(equalTo(200)));
    }

}
