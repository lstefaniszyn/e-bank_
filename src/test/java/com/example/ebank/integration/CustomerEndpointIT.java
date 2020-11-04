package com.example.ebank.integration;

import static org.hamcrest.Matchers.equalTo;

import com.capgemini.mrchecker.test.core.BaseTest;
import com.capgemini.mrchecker.test.core.logger.BFLogger;
import com.example.ebank.endpoints.CustomerEndpoint;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.junit.spring.integration.SpringIntegrationMethodRule;
import net.serenitybdd.rest.SerenityRest;

@RunWith(SerenityRunner.class)
@SpringBootTest
public class CustomerEndpointIT extends BaseTest {

    @Autowired
    private CustomerEndpoint endpoint;

    @Rule
    public SpringIntegrationMethodRule springIntegrationMethodRule = new SpringIntegrationMethodRule();

    @Test
    public void simpleGetQuery() {

        BFLogger.logInfo("Step 1 - Sending GET query to " + endpoint.getEndpoint());
        SerenityRest.get(endpoint.getEndpoint());

        BFLogger.logInfo("Step 2 - Validate response status code");
        SerenityRest.restAssuredThat(resp -> resp.statusCode(equalTo(200)));
    }

}
