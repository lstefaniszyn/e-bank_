package com.example.ebank.repositories;

import java.util.Date;

import com.example.ebank.models.Transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    Page<Transaction> findByValueDateBetween(Date startDate, Date endDate, Pageable pageable);
    
    Page<Transaction> findByValueDateBetweenAndAccountId(Date startDate, Date endDate, Long accountId, Pageable pageable);

}
