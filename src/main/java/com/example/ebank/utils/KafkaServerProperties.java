package com.example.ebank.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaServerProperties {
	
	@Value("${kafka.mock.during.start:false}")
	private boolean generateDataDuringStart;
	
	public boolean feedKafkaDuringStart() {
		return generateDataDuringStart;
	}
	
}
