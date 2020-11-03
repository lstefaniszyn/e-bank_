package com.example.ebank.api;

import static org.mockito.BDDMockito.given;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.capgemini.mrchecker.test.core.logger.BFLogger;
import com.example.ebank.controllers.AccountController;
import com.example.ebank.controllers.AppStatusController;
import com.example.ebank.controllers.CustomerController;
import com.example.ebank.controllers.TransactionController;
import com.example.ebank.models.Account;
import com.example.ebank.models.Currency;
import com.example.ebank.models.Customer;
import com.example.ebank.models.Transaction;
import com.example.ebank.services.AccountService;
import com.example.ebank.services.CustomerService;
import com.example.ebank.services.TransactionService;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(MockitoJUnitRunner.class)
public class AbstractContractSpec {
    
    @Mock
    AccountService accountService;
    
    @Mock
    TransactionService transactionService;
    
    @Mock
    CustomerService customerService;
    
    @InjectMocks
    CustomerController customerController;
    
    @InjectMocks
    AccountController accountController;
    
    @InjectMocks
    TransactionController transactionController;
    
    @InjectMocks
    AppStatusController appStatusController;
    
    @Before
    public void setup() {
        BFLogger.logDebug("!!!! TEST ME !!!");
        // Mock customer service
        given(customerService.getOne(1L)).willReturn(getCustomer(1L));
        given(customerService.getAll()).willReturn(getCustomers());
        
        // Mock account service
        given(accountService.getOne(1L)).willReturn(getAccount(1L, Currency.CHF));
        given(accountService.getAll()).willReturn(getAccounts());
        
        // Mock transaction service
        final String DATE_FORMAT = "yyyy-MM";
        final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ofPattern(DATE_FORMAT))
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();
        LocalDate date = LocalDate.parse("2019-01", DATE_FORMATTER);
        BFLogger.logDebug("Transactions: " + getPagedTransactions(0, 3));
        
        given(transactionService.findForAccountInMonthPaginated(1L, date, 0, 2)).willReturn(getPagedTransactions(0, 2));
        
        RestAssuredMockMvc.standaloneSetup(appStatusController, customerController, accountController, transactionController);
    }
    
    private List<Customer> getCustomers() {
        return List.of(
                getCustomer(1L),
                getCustomer(2L),
                getCustomer(3L),
                getCustomer(4L));
    }
    
    private Customer getCustomer(Long id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setGivenName(randomString(10));
        customer.setFamilyName(randomString(30));
        customer.setIdentityKey(randomNumber(12));
        return customer;
    }
    
    private List<Account> getAccounts() {
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
        account.setCustomer(this.getCustomer(1L));
        return account;
    }
    
    private Page<Transaction> getPagedTransactions(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        
        List<Transaction> transactions = getTransactions();
        
        int total = transactions.size();
        int start = Math.toIntExact(pageRequest.getOffset());
        int end = Math.min((start + pageRequest.getPageSize()), total);
        
        List<Transaction> output = new ArrayList<>();
        
        if (start <= end) {
            output = transactions.subList(start, end);
        }
        
        return new PageImpl<>(
                output,
                pageRequest,
                total);
    }
    
    private List<Transaction> getTransactions() {
        return List.of(
                getTransaction(1L),
                getTransaction(2L),
                getTransaction(3L),
                getTransaction(4L),
                getTransaction(5L));
    }
    
    private Transaction getTransaction(Long id) {
        Transaction transaction = new Transaction();
        transaction.setAmount(123.12);
        transaction.setCurrency(Currency.CHF);
        transaction.setDescription("Test_" + id);
        transaction.setId(id);
        transaction.setValueDate(new Date());
        return transaction;
    }
    
    static String randomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }
    
    static String randomNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }
}
