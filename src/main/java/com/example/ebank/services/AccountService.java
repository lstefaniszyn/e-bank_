package com.example.ebank.services;

import com.example.ebank.models.Account;
import com.example.ebank.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Iterable<Account> getAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findOne(Long id) {
        return accountRepository.findById(id);
    }
}
