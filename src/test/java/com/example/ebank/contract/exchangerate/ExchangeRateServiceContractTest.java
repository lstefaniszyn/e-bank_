package com.example.ebank.contract.exchangerate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties.StubsMode;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ebank.EBankApplication;
import com.example.ebank.extapi.client.FeignAPIClient;
import com.example.ebank.generated.dto.InlineResponse200Dto;
import com.example.ebank.models.Currency;

@RunWith(SpringRunner.class)
@AutoConfigureStubRunner(ids = "com.example:exchangerate:+:stubs:8095", stubsMode = StubsMode.LOCAL)
@SpringBootTest(classes = EBankApplication.class, webEnvironment = WebEnvironment.MOCK, properties = { "service.exchangerate.url=http://localhost:8095/" })
@DirtiesContext
@ActiveProfiles({ "mock", "it-local" })
public class ExchangeRateServiceContractTest {
	
	@Autowired
	private FeignAPIClient apiClient;
	
	@Test
	public void testExchangerateEndpoint() throws Exception {
		
		InlineResponse200Dto response = apiClient.getExchangeRate(Currency.EUR.getCode(), Currency.GBP.getCode());
		
		assertThat(response).isNotNull();
		assertThat(response.getValue()).isEqualByComparingTo(Double.valueOf("0.90872"));
	}
}
