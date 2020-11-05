package com.example.ebank.integration;

import static org.hamcrest.Matchers.equalTo;

import com.capgemini.mrchecker.test.core.logger.BFLogger;
import com.example.ebank.integration.endpoints.CustomerEndpoint;
import com.example.ebank.integration.serenity.SerenityReportBase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;

@RunWith(SerenityRunner.class)
@SpringBootTest
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
