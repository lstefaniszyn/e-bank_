package com.example.ebank.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

import com.example.ebank.models.Transaction;
import com.example.ebank.services.KafkaProducer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KafkaFeeder {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaFeeder.class);
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	private final KafkaProducer<Transaction> kafkaProducer;
	private final KafkaServerProperties properties;
	
	public KafkaFeeder(KafkaProducer<Transaction> kafkaProducer,
			KafkaServerProperties properties) {
		this.kafkaProducer = kafkaProducer;
		this.properties = properties;
	}
	
	@PostConstruct
	public void prepareMockedKafka() {
		if (properties.feedKafkaDuringStart()) {
			deleteTopics();
			createTopics();
			//feedKafkaTopic();
		} else {
			logger.info("Loading mocked data into kafka topics disabled!");
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
			topics.add(TopicBuilder.name("output-topic")
					.build());
			topics.add(TopicBuilder.name("input-topic")
					.build());
			
			adminClient.createTopics(topics);
		}
	}
	
	private void feedKafkaTopic() {
		List<Transaction> transactions = getMockedTransactions();
		
		transactions.forEach(t -> kafkaProducer.sendMessage(String.valueOf(t.getId()), t));
		
		logger.info("Kafka topics have been fed with mocked data!");
	}
	
	public static List<Transaction> getMockedTransactions(){
		List<Transaction> transactions = new ArrayList<>();
		Resource resource = new ClassPathResource("data/transactions_1_1.json");
		try {
			File file = resource.getFile();
			ObjectMapper jsonMapper = new ObjectMapper();
			transactions = jsonMapper.readValue(file, new TypeReference<List<Transaction>>() {
			});
		} catch (IOException exc) {
			logger.error("Error during loading transactions from file");
		}
		return transactions;
	}
	
}
