package com.example.ebank.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaServerProperties {
	
	@Value("${kafka.mock-during-start.enabled:false}")
	private boolean generateDataDuringStart;
	
	@Value("${kafka.read-mock-transactions.enabled:false}")
	private boolean readMockedTransactions;
	
	public boolean feedKafkaDuringStart() {
		return generateDataDuringStart;
	}
	
	public boolean readMockedTransactions() {
		return readMockedTransactions;
	}
	
}
