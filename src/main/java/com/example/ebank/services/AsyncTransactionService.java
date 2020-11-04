package com.example.ebank.services;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.ebank.models.Transaction;

/**
 * @author gwlodawiec
 *
 */
@Service
public class AsyncTransactionService {
	
	private static final Logger logger = LoggerFactory.getLogger(AsyncTransactionService.class);
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@Value("${default.kafka.output.topic.name}")
	private String outputTopic;
	
	@Autowired
	private ConsumerFactory<String, Transaction> transactionConsumerFactory;
	
	@Async("asyncExecutor")
	public CompletableFuture<Page<Transaction>> findInMonthPaginated(LocalDate date, int page, int size) {
		
		KafkaConsumer<String, Transaction> consumer = getConsumer();
		
		consumer.subscribe(Arrays.asList(outputTopic));
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		try {
			ConsumerRecords<String, Transaction> records = consumer.poll(Duration.ofSeconds(10));
			
			LocalDate startDate = date.withDayOfMonth(1);
	        LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());
			transactions = StreamSupport.stream(records.spliterator(), false)
					.filter(v -> {
						return v.value().getValueDate().after(Date.valueOf(startDate)) &&
								v.value().getValueDate().before(Date.valueOf(endDate));
					})
					.map(ConsumerRecord<String, Transaction>::value)
					.collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("There was error during polling messages: ", e);
		} finally {
			consumer.close();
		}
		
		return CompletableFuture.completedFuture(getPage(page, size, transactions));
	}
	
	@Async("asyncExecutor")
	public CompletableFuture<Page<Transaction>> findForAccountInMonthPaginated(Long accountId, LocalDate date, int page, int size) {
		KafkaConsumer<String, Transaction> consumer = getConsumer();
		consumer.subscribe(Arrays.asList(outputTopic));
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		//poll for messages and filter them
		
		return CompletableFuture.completedFuture(getPage(page, size, transactions));
		
	}
	
	private Properties getAdditionalConsumerProperties() {
		Properties props = new Properties();
		props.setProperty("group.id", UUID.randomUUID().toString());
		props.setProperty("enable.auto.commit", "false");
		props.setProperty("auto.offset.reset", "earliest");
		props.setProperty("max.poll.records", String.valueOf(Integer.MAX_VALUE));
		
		return props;
	}
	
	private Page<Transaction> getPage(int page, int size, List<Transaction> allTransactions) {
		Pageable pageable = PageRequest.of(page, size);
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), allTransactions.size());
		List<Transaction> transactions = allTransactions.subList(start, end);
		return new PageImpl<>(transactions, pageable, allTransactions.size());
	}
	
	private KafkaConsumer<String, Transaction> getConsumer() {
		KafkaConsumer<String, Transaction> consumer = (KafkaConsumer<String, Transaction>) transactionConsumerFactory.createConsumer(
				UUID.randomUUID().toString(),
				null, null, getAdditionalConsumerProperties());
		return consumer;
	}
	
}
