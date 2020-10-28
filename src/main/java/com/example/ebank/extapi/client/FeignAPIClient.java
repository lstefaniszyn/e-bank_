package com.example.ebank.extapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "exchangerate", url = "${service.exchangerate.url}")
public interface FeignAPIClient {

    @RequestMapping(method = RequestMethod.GET)
    public Double getExchangeRate(@RequestParam("baseCurrency") String baseCurrency,
            @RequestParam("targetCurrency") String targetCurrency);
}
