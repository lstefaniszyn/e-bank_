package com.example.exchangerate.microservice.services;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ExchangeRateMockService implements ExchangeRateService {

    private final Map<String, Double> exchangeRatesToEUR = Map.of("EUR", 1.0, "USD", 1.18550, "GBP", 0.90872, "CHF",
            1.07361);

    @Override
    public Double getExchangeRate(String baseCurrency, String targetCurrency) {
        Double targetRate = exchangeRatesToEUR.get(targetCurrency);
        Double baseRate = exchangeRatesToEUR.get(baseCurrency);
        if (targetRate == null || baseRate == null) {
            throw new RuntimeException("Unknown currency: " + baseCurrency + " or " + targetCurrency);
        }
        return targetRate / baseRate;
    }

}
