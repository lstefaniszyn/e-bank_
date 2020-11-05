package com.example.ebank.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.example.ebank.models.Transaction;

@Configuration
@EnableKafkaStreams
public class KafkaStreamConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaStreamConfig.class);
	
	@Value("${default.kafka.input.topic.name}")
	private String inputTopic;
	
	@Value("${default.kafka.output.topic.name}")
	private String outputTopic;
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;
	
	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;
	
	@SuppressWarnings("resource")
	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	public KafkaStreamsConfiguration kStreamsConfigs(KafkaProperties kafkaProperties) {
		
		Map<String, Object> config = new HashMap<>();
		config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		config.put(StreamsConfig.APPLICATION_ID_CONFIG, "other-group");
		config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String()
				.getClass());
		config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, new JsonSerde<Transaction>()
				.getClass());
		config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.ebank.models");
		return new KafkaStreamsConfiguration(config);
	}
	
	@Bean
	public KStream<String, Transaction> kStream(StreamsBuilder kStreamBuilder) {
		KStream<String, Transaction> stream = kStreamBuilder.stream(inputTopic);
		stream.mapValues(v -> {
			logger.info(String.format("Processing: [%s]", v));
			return v;
		})
				.to(outputTopic);
		return stream;
	}
	
}
