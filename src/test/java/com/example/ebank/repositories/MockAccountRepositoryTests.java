package com.example.ebank.repositories;

import com.example.ebank.models.Account;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
