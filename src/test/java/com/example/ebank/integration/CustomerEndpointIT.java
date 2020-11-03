package com.example.ebank.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.capgemini.mrchecker.test.core.BaseTest;
import com.capgemini.mrchecker.test.core.logger.BFLogger;
import com.capgemini.mrchecker.test.core.utils.PageFactory;
import com.example.ebank.endpoints.CustomerEndpoint;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.restassured.response.Response;

@SpringBootTest
public class CustomerEndpointIT extends BaseTest {

    @Test
    public void simpleGetQuery() {
        CustomerEndpoint endpoint = PageFactory.getPageInstance(CustomerEndpoint.class);

        BFLogger.logInfo("Step 1 - Sending GET query to " + endpoint.getEndpoint());
        Response response = endpoint.sendGetQuery();

        BFLogger.logInfo("Step 2 - Validate response status code");
        assertThat(response.statusCode(), is(200));
    }

}
