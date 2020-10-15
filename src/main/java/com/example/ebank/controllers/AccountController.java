package com.example.ebank.controllers;

import com.example.ebank.models.Account;
import com.example.ebank.services.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public Iterable<Account> list() {
        return accountService.getAll();
    }

    @GetMapping("/{id}")
    public Account get(@PathVariable Long id) {
        return accountService.findOne(id).orElseThrow(EntityNotFoundException::new);
    }

}
