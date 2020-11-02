package com.example.exchangerate.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ExchangeRateApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "exchange-rate");
        SpringApplication.run(ExchangeRateApplication.class, args);
    }

}
