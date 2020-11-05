package com.example.ebank.communication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionConsumer.class);
	
	@KafkaListener(topics = "output-topic", groupId = "default-group")
	public void consume(String message) {
		logger.info(String.format("Consumed message: %s", message));
		
	}
	
}
