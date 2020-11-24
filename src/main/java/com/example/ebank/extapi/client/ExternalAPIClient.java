package com.example.ebank.extapi.client;

import com.example.ebank.generated.dto.InlineResponse200Dto;
import com.example.ebank.models.Currency;

public interface ExternalAPIClient {

    Currency TARGET_CURRENCY = Currency.GBP;

    InlineResponse200Dto getExchangeRate(Currency currency);

    default Currency getTargetCurrency() {
        return TARGET_CURRENCY;
    }
}
