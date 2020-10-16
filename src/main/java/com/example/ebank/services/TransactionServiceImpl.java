package com.example.ebank.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import com.example.ebank.models.Transaction;
import com.example.ebank.repositories.TransactionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

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

    @Override
    public Page<Transaction> findInMonthPaginated(LocalDate date, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        LocalDate startDate = date.withDayOfMonth(1);
        LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());
        return transactionRepository.findByValueDateBetween(Date.valueOf(startDate), Date.valueOf(endDate), pageable);
    }
}
