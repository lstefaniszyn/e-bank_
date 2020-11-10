package com.example.ebank.communication;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.ebank.models.TransactionRequest;
import com.example.ebank.services.KafkaProducer;

@Service
public class TransactionRequestProducer {
	
	private final KafkaProducer<TransactionRequest> kafkaProducer;
	
	public TransactionRequestProducer(KafkaProducer<TransactionRequest> kafkaProducer) {
		this.kafkaProducer = kafkaProducer;
	}
	
	//@Scheduled(fixedDelay = 2000)
	@Async("asyncExecutor")
	public CompletableFuture<TransactionRequest> send() {
		TransactionRequest request = new TransactionRequest(1l, 1l, LocalDate.of(2018, 01, 01));
		kafkaProducer.sendMessage(request);
		return CompletableFuture.completedFuture(request);
	}
	
}
