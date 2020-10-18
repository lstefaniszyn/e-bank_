package com.example.ebank.services;

import java.time.LocalDate;

import com.example.ebank.models.Transaction;

import org.springframework.data.domain.Page;

public interface TransactionService {

    public Page<Transaction> findInMonthPaginated(LocalDate date, int page, int size);
}
