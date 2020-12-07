package com.example.ebank.services;

import com.example.ebank.models.Transaction;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface TransactionService {

    Page<Transaction> findForAccountInMonthPaginated(Long accountId, LocalDate date, int page, int size);

}
