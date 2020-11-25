package com.example.ebank.services;

import com.example.ebank.extapi.client.ExternalAPIClient;
import com.example.ebank.generated.dto.InlineResponse200Dto;
import com.example.ebank.models.Currency;
import com.example.ebank.models.Transaction;
import com.example.ebank.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private ExternalAPIClient client;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
        @Value("${service.exchangerate.client}") String clientId, ApplicationContext context) {
        this.transactionRepository = transactionRepository;
        this.client = (ExternalAPIClient) context.getBean(clientId);
    }

    public Page<Transaction> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepository.findAll(pageable);
    }

    public Optional<Transaction> findOne(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Page<Transaction> findInMonthPaginated(LocalDate date, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "date"));
        LocalDate startDate = date.withDayOfMonth(1);
        LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());
        Page<Transaction> transactionsPage = transactionRepository.findByDateBetween(Date.valueOf(startDate),
            Date.valueOf(endDate), pageable);
        Map<Currency, InlineResponse200Dto> exchangeRates = new HashMap<>();
        return transactionsPage.map(t -> {
            if (t.getCurrency() != client.getTargetCurrency()) {
            	InlineResponse200Dto exchangeRate = exchangeRates.computeIfAbsent(t.getCurrency(), client::getExchangeRate);
                if (exchangeRate != null) {
                    t.setCurrency(client.getTargetCurrency());
                    t.setAmount(t.getAmount() * exchangeRate.getValue());
                }
            }
            return t;
        });
    }

    @Override
    public Page<Transaction> findForAccountInMonthPaginated(Long accountId, LocalDate date, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "date"));
        LocalDate startDate = date.withDayOfMonth(1);
        LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());
        return transactionRepository.findByDateBetweenAndAccountId(Date.valueOf(startDate), Date.valueOf(endDate),
            accountId, pageable);
    }

}
