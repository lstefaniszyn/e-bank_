package com.example.ebank.services;

import com.example.ebank.models.Account;
import com.example.ebank.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AccountService {
    
    private final AccountRepository accountRepository;
    
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllForCustomer(Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    public Account getOneForCustomer(Long id, Long customerId) {
        return accountRepository.findByIdAndCustomerId(id, customerId).orElseThrow(EntityNotFoundException::new);
    }
}
