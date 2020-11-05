package com.example.ebank.communication;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.ebank.models.Currency;
import com.example.ebank.models.Transaction;
import com.example.ebank.models.TransactionRequest;
import com.example.ebank.services.KafkaProducer;

@Service
public class TransactionRequestProducer {
	
	private final KafkaProducer<TransactionRequest> kafkaProducer;
	private final KafkaProducer<Transaction> transactionProducer;
	
	public TransactionRequestProducer(KafkaProducer<TransactionRequest> kafkaProducer,
			KafkaProducer<Transaction> transactionProducer) {
		this.kafkaProducer = kafkaProducer;
		this.transactionProducer = transactionProducer;
	}
	
	// @Scheduled(fixedDelay = 2000)
	public void send() {
		kafkaProducer.sendMessage(new TransactionRequest(1l, 1l, LocalDate.of(2020, 10, 27)));
	}
	
	@Scheduled(fixedDelay = 2000)
	public void sendMockedTransaction() {
		Transaction transaction = new Transaction();
		transaction.setAmount(Double.valueOf("123.45"));
		transaction.setCurrency(Currency.GBP);
		transaction.setDescription("Testowa transakcja");
		transaction.setValueDate(Date.valueOf(LocalDate.now()));
		transaction.setId(9346L);
		transactionProducer.sendMessage(transaction);
	}
	
}
