package com.example.ebank.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	
	@KafkaListener(topics = "output-topic", groupId = "default-group")
	public void consume(String message) {
		logger.info(String.format("Consumed message: %s", message));
	}

}
