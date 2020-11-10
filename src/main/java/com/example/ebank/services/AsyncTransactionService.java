package com.example.ebank.services;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.example.ebank.models.Transaction;
import com.example.ebank.utils.logger.BFLogger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
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

/**
 * @author gwlodawiec
 *
 */
@Service
public class AsyncTransactionService {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

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

            LocalDate startDate = date.withDayOfMonth(1);
            LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());
//            transactions = StreamSupport.stream(records.spliterator(), false).filter(v -> {
//                return v.value().getValueDate().after(Date.valueOf(startDate))
//                        && v.value().getValueDate().before(Date.valueOf(endDate));
//            }).map(ConsumerRecord<String, Transaction>::value).collect(Collectors.toList());
            records.forEach(v -> transactions.addAll(v.value()));
        } catch (Exception e) {
            BFLogger.logError("There was error during polling messages: " + e.toString());
        } finally {
            consumer.close();
        }

        return CompletableFuture.completedFuture(getPage(page, size, transactions));
    }

    @Async("asyncExecutor")
    public CompletableFuture<Page<Transaction>> findForAccountInMonthPaginated(Long accountId, LocalDate date, int page,
            int size) {
        KafkaConsumer<String, List<Transaction>> consumer = getConsumer();
        consumer.subscribe(Arrays.asList(outputTopic));

        List<Transaction> transactions = new ArrayList<Transaction>();

        // poll for messages and filter them

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

    private KafkaConsumer<String, List<Transaction>> getConsumer() {
        KafkaConsumer<String, List<Transaction>> consumer = (KafkaConsumer<String, List<Transaction>>) transactionConsumerFactory
                .createConsumer(UUID.randomUUID().toString(), null, null, getAdditionalConsumerProperties());
        return consumer;
    }

}
