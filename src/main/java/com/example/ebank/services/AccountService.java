package com.example.ebank.services;

import com.example.ebank.models.Account;
import com.example.ebank.repositories.AccountRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Iterable<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account getOne(Long id) {
        return accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
    
    public Page<Account> getForCustomer(Long customerId, int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
    	return accountRepository.findByCustomerId(customerId, pageable);
    }
}
