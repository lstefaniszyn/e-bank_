package com.example.ebank.integration;

import static org.hamcrest.Matchers.equalTo;

import com.capgemini.mrchecker.test.core.BaseTest;
import com.capgemini.mrchecker.test.core.logger.BFLogger;
import com.capgemini.mrchecker.test.core.utils.PageFactory;
import com.example.ebank.endpoints.CustomerEndpoint;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;

@RunWith(SerenityRunner.class)
@SpringBootTest
public class CustomerEndpointIT extends BaseTest {

    @Test
    public void simpleGetQuery() {
        CustomerEndpoint endpoint = PageFactory.getPageInstance(CustomerEndpoint.class);

        BFLogger.logInfo("Step 1 - Sending GET query to " + endpoint.getEndpoint());
        SerenityRest.get(endpoint.getEndpoint());

        BFLogger.logInfo("Step 2 - Validate response status code");
        SerenityRest.restAssuredThat(resp -> resp.statusCode(equalTo(200)));
    }

}
