package com.example.ebank.extapi.client;

import com.example.ebank.models.Currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("feign")
public class ExchangeRateFeignClient implements ExternalAPIClient {

    @Autowired
    FeignAPIClient feignAPIClient;

    @Override
    public Double getExchangeRate(Currency currency) {
        try {
            return feignAPIClient.getExchangeRate(currency.toString(), getTargetCurrency().toString());
        } catch (Exception e) {
            return null;
        }
    }

}
