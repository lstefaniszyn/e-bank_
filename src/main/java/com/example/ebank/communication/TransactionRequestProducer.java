package com.example.ebank.communication;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.ebank.models.TransactionRequest;
import com.example.ebank.services.KafkaProducerService;

@Service
public class TransactionRequestProducer {
	
	private final KafkaProducerService<TransactionRequest> kafkaProducer;
	
	public TransactionRequestProducer(KafkaProducerService<TransactionRequest> kafkaProducer) {
		this.kafkaProducer = kafkaProducer;
	}
	
	@Async("asyncExecutor")
	public CompletableFuture<TransactionRequest> send(Long customerId, Long accountId, LocalDate date) {
		TransactionRequest request = new TransactionRequest(customerId, accountId, date);
		kafkaProducer.sendMessage(request);
		return CompletableFuture.completedFuture(request);
	}
	
}
