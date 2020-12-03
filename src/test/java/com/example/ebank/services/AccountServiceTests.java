package com.example.ebank.services;

import com.example.ebank.models.Account;
import com.example.ebank.models.Currency;
import com.example.ebank.repositories.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static com.example.ebank.services.CustomerServiceTests.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTests {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void testGetAllForCustomer_expectOk() {
        long customerId = 68L;
        when(accountRepository.findByCustomerId(customerId)).thenReturn(getList());

        Iterable<Account> result = accountService.getAllForCustomer(customerId);
        assertThat(result).isNotEmpty()
            .hasSize(5);
    }

    @Test
    public void testGetOneForCustomer_expectOk() {
        long id = 157L;
        long customerId = 68L;
        when(accountRepository.findByIdAndCustomerId(id, customerId))
            .thenReturn(Optional.of(getAccount(id, Currency.CHF)));

        Account result = accountService.getOneForCustomer(id, customerId);

        assertThat(result).isNotNull();
    }

    @Test
    public void testGetOneForCustomer_expectEntityNotFoundException() {
        long id = 157L;
        long customerId = 47L;
        when(accountRepository.findByIdAndCustomerId(id, customerId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.getOneForCustomer(id, customerId)).isInstanceOf(EntityNotFoundException.class);

    }

    private List<Account> getList() {
        return List.of(
            getAccount(1L, Currency.CHF),
            getAccount(2L, Currency.GBP),
            getAccount(3L, Currency.CHF),
            getAccount(4L, Currency.EUR),
            getAccount(5L, Currency.GBP));
    }

    private Account getAccount(Long id, Currency currency) {
        Account account = new Account();
        account.setId(id);
        account.setIban(randomString(20));
        account.setCurrency(currency);
        return account;
    }

}
