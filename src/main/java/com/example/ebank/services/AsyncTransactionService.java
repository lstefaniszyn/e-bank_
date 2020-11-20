package com.example.ebank.services;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
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
import com.example.ebank.utils.logger.BFLogger;

/**
 * @author gwlodawiec
 */
@Service
public class AsyncTransactionService {

    @Value("${default.kafka.output.topic.name}")
    private String outputTopic;

    @Autowired
    private ConsumerFactory<String, List<Transaction>> transactionConsumerFactory;

    @Async("asyncExecutor")
    public CompletableFuture<Page<Transaction>> findInMonthPaginated(LocalDate date, int page, int size) {

        KafkaConsumer<String, List<Transaction>> consumer = getConsumer();

        consumer.subscribe(Arrays.asList(outputTopic));
        List<Transaction> transactions = new ArrayList<Transaction>();

        try {
            ConsumerRecords<String, List<Transaction>> records = consumer.poll(Duration.ofSeconds(10));
            records.forEach(v -> transactions.addAll(v.value()));
        } catch (Exception e) {
            BFLogger.logError("There was error during polling messages: " + e.toString());
        } finally {
            consumer.close();
        }

        return CompletableFuture.completedFuture(getPageSortedByDate(page, size, transactions));
    }

    @Async("asyncExecutor")
    public CompletableFuture<Page<Transaction>> findForAccountInMonthPaginated(Long accountId, LocalDate date, int page,
            int size) {
        KafkaConsumer<String, List<Transaction>> consumer = getConsumer();
        consumer.subscribe(Arrays.asList(outputTopic));

        List<Transaction> transactions = new ArrayList<Transaction>();

        // poll for messages and filter them

        return CompletableFuture.completedFuture(getPageSortedByDate(page, size, transactions));

    }

    private Properties getAdditionalConsumerProperties() {
        Properties props = new Properties();
        props.setProperty("group.id", UUID.randomUUID().toString());
        props.setProperty("enable.auto.commit", "false");
        props.setProperty("auto.offset.reset", "earliest");
        props.setProperty("max.poll.records", String.valueOf(Integer.MAX_VALUE));

        return props;
    }

    private Page<Transaction> getPageSortedByDate(int page, int size, List<Transaction> allTransactions) {
        Collections.sort(allTransactions, (t1, t2) -> t1.getDate().compareTo(t2.getDate()));
        return getPage(page, size, allTransactions);
    }

    private Page<Transaction> getPage(int page, int size, List<Transaction> allTransactions) {
        Pageable pageable = PageRequest.of(page, size);
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), allTransactions.size());
        List<Transaction> transactions = allTransactions.subList(start, end);
        return new PageImpl<Transaction>(transactions, pageable, size);
    }

    private KafkaConsumer<String, List<Transaction>> getConsumer() {
        KafkaConsumer<String, List<Transaction>> consumer = (KafkaConsumer<String, List<Transaction>>) transactionConsumerFactory
                .createConsumer(UUID.randomUUID().toString(), null, null, getAdditionalConsumerProperties());
        return consumer;
    }

}
