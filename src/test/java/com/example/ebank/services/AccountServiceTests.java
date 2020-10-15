package com.example.ebank.services;

import com.example.ebank.repositories.AccountRepository;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.when;

public class AccountServiceTests {

    @Mock
    private AccountRepository accountRepository;


    public void testGetAll() {
        when(accountRepository.findAll()).thenReturn(List.of());
    }
}
