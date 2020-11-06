package com.example.ebank.services;

import com.example.ebank.utils.logger.BFLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducer<T> {

    @Autowired
    private KafkaTemplate<String, T> kafkaTemplate;

    @Value(value = "${default.kafka.input.topic.name}")
    private String _TOPIC;

    public void sendMessage(T message) {
        sendMessage(null, message);
    }

    public void sendMessage(String key, T message) {

        ListenableFuture<SendResult<String, T>> future = kafkaTemplate.send(_TOPIC, key, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, T>>() {

            @Override
            public void onSuccess(SendResult<String, T> result) {
                BFLogger.logInfo(String.format("Message has been sent! [%s]", message));
            }

            @Override
            public void onFailure(Throwable ex) {
                BFLogger.logError(String.format("Unable to send message due to exception: %s", ex.getMessage()));
            }
        });
    }

}
