package com.example.ebank.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import com.example.ebank.models.Transaction;
import com.example.ebank.services.KafkaProducer;
import com.example.ebank.utils.logger.BFLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaFeeder {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private final KafkaProducer<Transaction> kafkaProducer;
    private final KafkaServerProperties properties;

    public KafkaFeeder(KafkaProducer<Transaction> kafkaProducer, KafkaServerProperties properties) {
        this.kafkaProducer = kafkaProducer;
        this.properties = properties;
    }

    @PostConstruct
    public void prepareMockedKafka() {
        if (properties.feedKafkaDuringStart()) {
            deleteTopics();
            createTopics();
            feedKafkaTopic();
        } else {
            BFLogger.logInfo("Loading mocked data into kafka topics disabled!");
        }
    }

    private void deleteTopics() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", bootstrapServers);

        try (AdminClient adminClient = AdminClient.create(props)) {
            List<String> topics = new ArrayList<String>();
            topics.add("input-topic");
            topics.add("output-topic");

            adminClient.deleteTopics(topics);
        }
    }

    private void createTopics() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", bootstrapServers);

        try (AdminClient adminClient = AdminClient.create(props)) {
            List<NewTopic> topics = new ArrayList<NewTopic>();
            topics.add(TopicBuilder.name("output-topic").build());
            topics.add(TopicBuilder.name("input-topic").build());

            adminClient.createTopics(topics);
        }
    }

    private void feedKafkaTopic() {
        List<Transaction> transactions = new ArrayList<>();
        Resource resource = new ClassPathResource("data/transactions_1_1.json");
        try {
            File file = resource.getFile();
            ObjectMapper jsonMapper = new ObjectMapper();
            transactions = jsonMapper.readValue(file, new TypeReference<List<Transaction>>() {
            });
        } catch (IOException exc) {
            BFLogger.logError("Error during loading transactions from file");
        }

        transactions.forEach(t -> kafkaProducer.sendMessage(String.valueOf(t.getId()), t));

        BFLogger.logInfo("Kafka topics have been fed with mocked data!");
    }

}
