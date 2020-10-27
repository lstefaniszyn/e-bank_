package com.example.ebank.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import com.example.ebank.models.Currency;
import com.example.ebank.models.Transaction;
import com.example.ebank.repositories.TransactionRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final static Currency TARGET_CURRENCY = Currency.GBP;
    private final RestTemplate restTemplate;
    @Value("${service.exchangerate.url}")
    private String exchangeRateServiceURL;

    public TransactionServiceImpl(TransactionRepository transactionRepository, RestTemplate restTemplate) {
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
        if (this.restTemplate != null) {
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
            this.restTemplate.setMessageConverters(List.of(converter));
        }
    }

    public Page<Transaction> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepository.findAll(pageable);
    }

    public Optional<Transaction> findOne(Long id) {
        return transactionRepository.findById(id);
    }

    private Double getExchangeRate(Currency currency) {
        Map<String, String> exchangeRateParams = Map.of("baseCurrency", currency.toString(), "targetCurrency",
                TARGET_CURRENCY.toString());
        try {
            return restTemplate.getForObject(
                    exchangeRateServiceURL
                            + "/exchangeRate?baseCurrency={baseCurrency}&targetCurrency={targetCurrency}",
                    Double.class, exchangeRateParams);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<Transaction> findInMonthPaginated(LocalDate date, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        LocalDate startDate = date.withDayOfMonth(1);
        LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());
        Page<Transaction> transactionsPage = transactionRepository.findByValueDateBetween(Date.valueOf(startDate),
                Date.valueOf(endDate), pageable);
        Map<Currency, Double> exchangeRates = new HashMap<>();
        return transactionsPage.map(t -> {
            if (t.getCurrency() != TARGET_CURRENCY) {
                Transaction converted = new Transaction();
                converted.setId(t.getId());
                converted.setDescription(t.getDescription());
                converted.setValueDate(t.getValueDate());
                converted.setAmount(t.getAmount());
                converted.setCurrency(t.getCurrency());
                Double exchangeRate = exchangeRates.computeIfAbsent(t.getCurrency(), this::getExchangeRate);
                if (exchangeRate != null) {
                    converted.setCurrency(TARGET_CURRENCY);
                    converted.setAmount(t.getAmount() * exchangeRate);
                }
                return converted;
            } else {
                return t;
            }
        });
    }

    @Override
    public Page<Transaction> findForAccountInMonthPaginated(Long accountId, LocalDate date, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        LocalDate startDate = date.withDayOfMonth(1);
        LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());
        return transactionRepository.findByValueDateBetweenAndAccountId(Date.valueOf(startDate), Date.valueOf(endDate),
                accountId, pageable);
    }

}
