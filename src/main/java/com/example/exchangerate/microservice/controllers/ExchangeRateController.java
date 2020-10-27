package com.example.exchangerate.microservice.controllers;

import com.example.exchangerate.microservice.services.ExchangeRateService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService service) {
        this.exchangeRateService = service;
    }

    @GetMapping("/exchangeRate")
    public Double getExchangeRate(@RequestParam(name = "baseCurrency") String baseCurrency,
            @RequestParam(name = "targetCurrency") String targetCurrency) {
        return exchangeRateService.getExchangeRate(baseCurrency, targetCurrency);
    }
}
