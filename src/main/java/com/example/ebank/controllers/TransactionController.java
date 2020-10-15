package com.example.ebank.controllers;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.example.ebank.models.Transaction;
import com.example.ebank.services.TransactionService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> list(@RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "2") int size) {
        Page<Transaction> resultsPage = transactionService.getAll(page, size);
        return resultsPage.getContent();
    }

    @GetMapping("/{id}")
    public Transaction get(@PathVariable Long id) {
        return transactionService.findOne(id).orElseThrow(EntityNotFoundException::new);
    }
}
