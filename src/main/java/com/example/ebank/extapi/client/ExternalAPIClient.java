package com.example.ebank.extapi.client;

import com.example.ebank.models.Currency;

public interface ExternalAPIClient {

    public static final Currency TARGET_CURRENCY = Currency.GBP;

    public Double getExchangeRate(Currency currency);

    default public Currency getTargetCurrency() {
        return TARGET_CURRENCY;
    }
}
