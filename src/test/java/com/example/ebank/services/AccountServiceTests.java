package com.example.ebank.services;

import com.example.ebank.models.Account;
import com.example.ebank.models.Currency;
import com.example.ebank.models.Customer;
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
    public void testGetAll_expectOk() {
        when(accountRepository.findAll()).thenReturn(getList());

        Iterable<Account> result = accountService.getAll();
        assertThat(result).isNotEmpty()
                .hasSize(5);
    }

    @Test
    public void testFindOne_expectOk() {
        long id = 157L;
        when(accountRepository.findById(id))
                .thenReturn(Optional.of(getAccount(id, Currency.CHF)));

        Account result = accountService.getOne(id);

        assertThat(result).isNotNull();
    }

    @Test
    public void testFindOne_expectEntityNotFoundException() {
        long id = 157L;
        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.getOne(id)).isInstanceOf(EntityNotFoundException.class);

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
        account.setCustomer(new Customer());
        return account;
    }

}
