package com.example.ebank.repositories;

import com.example.ebank.models.Transaction;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

}
