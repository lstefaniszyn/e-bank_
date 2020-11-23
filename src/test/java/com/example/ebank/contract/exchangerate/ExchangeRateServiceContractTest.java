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

@RunWith(SpringRunner.class)
@AutoConfigureStubRunner(ids = "com.example:exchangerate:+:stubs:8095", stubsMode = StubsMode.LOCAL)
@SpringBootTest(classes = EBankApplication.class, webEnvironment = WebEnvironment.MOCK)
@DirtiesContext
@ActiveProfiles("mock")
public class ExchangeRateServiceContractTest {
	
	@Autowired
	@Qualifier("resttemplate")
	private ExternalAPIClient exchangeRateService;
	
	@Test
	public void testExchangerateEndpoint() {
		
	}
	
}
