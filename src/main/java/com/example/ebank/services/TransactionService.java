package com.example.ebank.services;

import java.util.List;
import java.util.Optional;

import com.example.ebank.models.Transaction;
import com.example.ebank.repositories.TransactionRepository;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> findOne(Long id) {
        return transactionRepository.findById(id);
    }
}
