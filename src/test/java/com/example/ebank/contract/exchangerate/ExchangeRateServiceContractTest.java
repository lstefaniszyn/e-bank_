package com.example.ebank.contract.exchangerate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties.StubsMode;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ebank.EBankApplication;
import com.example.ebank.extapi.client.ExternalAPIClient;
import com.example.ebank.generated.dto.InlineResponse200Dto;
import com.example.ebank.models.Currency;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureStubRunner(ids = "com.example:exchangerate:+:stubs:8095", stubsMode = StubsMode.LOCAL)
@SpringBootTest(classes = EBankApplication.class, webEnvironment = WebEnvironment.MOCK)
@DirtiesContext
@ActiveProfiles({ "mock", "it-local" })
public class ExchangeRateServiceContractTest {
	
	@Autowired
	@Qualifier("feign")
	private ExternalAPIClient exchangeRateService;
	
	@Test
	public void testExchangerateEndpoint() throws Exception {
		
		InlineResponse200Dto response = exchangeRateService.getExchangeRate(Currency.EUR);
		
		assertThat(response).isNotNull();
		assertThat(response.getValue()).isEqualByComparingTo(Double.valueOf("0.90872"));
	}
}
