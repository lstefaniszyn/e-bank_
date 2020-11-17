package com.example.ebank.extapi.client;

import com.example.ebank.models.Currency;

public interface ExternalAPIClient {

    Currency TARGET_CURRENCY = Currency.GBP;

    Double getExchangeRate(Currency currency);

    default Currency getTargetCurrency() {
        return TARGET_CURRENCY;
    }
}
