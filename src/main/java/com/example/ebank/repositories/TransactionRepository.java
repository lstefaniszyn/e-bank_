package com.example.ebank.repositories;

import java.util.Date;

import com.example.ebank.models.Transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    Page<Transaction> findByDateBetweenAndAccountId(Date startDate, Date endDate, Long accountId, Pageable pageable);

}
