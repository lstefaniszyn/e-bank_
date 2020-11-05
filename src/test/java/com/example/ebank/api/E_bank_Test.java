package com.example.ebank.api;

import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import com.example.ebank.utils.logger.BFLogger;
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

    @Test
    public void validate_get_account_for_one_customer_OK_Response() throws Exception {
        // given:
        MockMvcRequestSpecification request = given();

        // when:
        ResponseOptions response = given().spec(request)
                .get("/api/v1/customers/{idCustomer}/accounts/{idAccount}", 1, 1);

        // then:
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.header("Content-Type")).isEqualTo("application/json");
        // and:
        DocumentContext parsedJson = JsonPath.parse(response.getBody()
                .asString());
        assertThatJson(parsedJson).field("['name']")
                .isEqualTo("1");
        assertThatJson(parsedJson).field("['currency.name']")
                .isEqualTo("Euro");
        assertThatJson(parsedJson).field("['currency.code']")
                .isEqualTo("EUR");
        assertThatJson(parsedJson).field("['id']")
                .isEqualTo("1");
    }

    @Test
    public void validate_get_transactions_for_account_for_customer_OK_Response() throws Exception {
        // given:
        MockMvcRequestSpecification request = given();

        // when:
        ResponseOptions response = given().spec(request)
                .queryParam("date", "2019-01")
                .queryParam("page", 0)
                .queryParam("size", 2)
                .get("/api/v1/customers/{idCustomer}/accounts/{idAccount}/transactions", 1, 1);

        // then:
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.header("Content-Type")).isEqualTo("application/json");
        // and:
        DocumentContext parsedJson = JsonPath.parse(response.getBody()
                .asString());
        // and:
        assertThat(parsedJson.read("$[0].id", String.class)).matches("[\\d]+");
        assertThat(parsedJson.read("$[0].value.amount", String.class)).matches("[\\d]+");
        assertThat(parsedJson.read("$[0].value.currency.name", String.class)).matches("[\\w]+");
        assertThat(parsedJson.read("$[0].value.currency.code", String.class)).matches("[\\w]+");
        assertThat(parsedJson.read("$[0].iban", String.class)).matches("\\w\\w[\\d]{16,24}");
        assertThat(parsedJson.read("$[0].date", String.class)).matches("\\d{4}-\\d{2}-\\d{2}(T|t)\\d{2}:\\d{2}:\\d{2}[\\w]?");
        assertThat(parsedJson.read("$[0].description", String.class)).matches("[\\w]*");
    }

}
