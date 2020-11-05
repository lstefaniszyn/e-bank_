package com.example.ebank.communication;

import java.time.LocalDate;

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
	
	@Scheduled(fixedDelay = 2000)
	public void send() {
		kafkaProducer.sendMessage(new TransactionRequest(1l, 1l, LocalDate.of(2020, 10, 27)));
	}
	
}
