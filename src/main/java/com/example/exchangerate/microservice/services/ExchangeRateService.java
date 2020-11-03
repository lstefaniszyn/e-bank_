package com.example.exchangerate.microservice.services;

public interface ExchangeRateService {

    Double getExchangeRate(String baseCurrency, String targetCurrency);

}
