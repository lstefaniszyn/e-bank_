package com.example.ebank.controllers;

import com.example.ebank.models.Account;
import com.example.ebank.repositories.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public Iterable<Account> list() {
        return accountRepository.findAll();
    }

    @GetMapping("/{id}")
    public Account get(@PathVariable UUID id) {
        return accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
