package com.example.ebank.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.ebank.models.Currency;
import com.example.ebank.models.Transaction;
import com.example.ebank.repositories.TransactionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final static Currency TARGET_CURRENCY = Currency.GBP;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Page<Transaction> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepository.findAll(pageable);
    }

    public Optional<Transaction> findOne(Long id) {
        return transactionRepository.findById(id);
    }

    private static double getExchangeRate(Currency currency) {
        return 2.0;
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
                converted.setCurrency(TARGET_CURRENCY);
                converted.setAmount(t.getAmount()
                        * exchangeRates.computeIfAbsent(t.getCurrency(), TransactionServiceImpl::getExchangeRate));
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
