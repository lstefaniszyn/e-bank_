package com.example.ebank.extapi.client;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.example.ebank.models.Currency;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("resttemplate")
public class ExchangeRateRestTemplateClient implements ExternalAPIClient {

    private final RestTemplate restTemplate;
    @Value("${service.exchangerate.url}")
    private String exchangeRateServiceURL;

    public ExchangeRateRestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        if (this.restTemplate != null) {
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
            this.restTemplate.setMessageConverters(List.of(converter));
        }
    }

    @Override
    public Double getExchangeRate(Currency currency) {
        Map<String, String> exchangeRateParams = Map.of("baseCurrency", currency.toString(), "targetCurrency",
                getTargetCurrency().toString());
        try {
            return restTemplate.getForObject(
                    exchangeRateServiceURL
                            + "/exchangeRate?baseCurrency={baseCurrency}&targetCurrency={targetCurrency}",
                    Double.class, exchangeRateParams);
        } catch (Exception e) {
            return null;
        }
    }
}
