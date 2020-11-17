package com.example.ebank.communication;

import java.util.List;

import com.example.ebank.models.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {

	private static final Logger logger = LoggerFactory.getLogger(TransactionConsumer.class);

//	@KafkaListener(topics = "${default.kafka.output.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void consume(List<Transaction> message) {
		logger.info(String.format("Consumed message: %s", message));
	}

}
