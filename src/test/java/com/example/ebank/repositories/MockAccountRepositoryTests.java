package com.example.ebank.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.example.ebank.models.Account;

public class MockAccountRepositoryTests {
    
    private final MockAccountRepository accountRepository = new MockAccountRepository();
    
    @Test
    public void testFindAllAccounts() {
        List<Account> result = accountRepository.findAll();
        
        assertThat(result).isNotEmpty();
    }
    
    @Test
    public void testFindAccountById_expectPresent() {
        Optional<Account> result = accountRepository.findById(1L);
        
        assertThat(result).isNotEmpty();
    }
    
    @Test
    public void testFindAccountById_expectEmpty() {
        Optional<Account> result = accountRepository.findById(97655L);
        
        assertThat(result).isEmpty();
    }
}
