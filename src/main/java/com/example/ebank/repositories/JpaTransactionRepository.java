package com.example.ebank.repositories;

import com.example.ebank.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

public interface JpaTransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    Page<Transaction> findByDateBetween(Date startDate, Date endDate, Pageable pageable);

    Page<Transaction> findByDateBetweenAndAccountId(Date startDate, Date endDate, Long accountId, Pageable pageable);

}
