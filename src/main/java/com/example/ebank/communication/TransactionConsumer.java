package com.example.ebank.communication;

import java.util.List;

import com.example.ebank.models.Transaction;
import com.example.ebank.utils.logger.BFLogger;

import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {
    
    // @KafkaListener(topics = "${default.kafka.output.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(List<Transaction> message) {
        BFLogger.logInfo(String.format("Consumed message: %s", message));
    }
    
}
