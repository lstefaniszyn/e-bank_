package com.example.ebank.extapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ebank.autogenerated.dto.InlineResponse200Dto;

@FeignClient(name = "exchangerate", url = "${service.exchangerate.url}")
public interface FeignAPIClient {
	
	@RequestMapping(method = RequestMethod.GET, path = "/exchangeRate", consumes = "application/json")
	InlineResponse200Dto getExchangeRate(@RequestParam("baseCurrency") String baseCurrency,
			@RequestParam("targetCurrency") String targetCurrency);
}
