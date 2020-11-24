package com.example.ebank.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.ebank.communication.TransactionRequestProducer;
import com.example.ebank.generated.api.AccountApi;
import com.example.ebank.generated.dto.AccountDto;
import com.example.ebank.generated.dto.TransactionPageDto;
import com.example.ebank.mappers.AccountMapper;
import com.example.ebank.mappers.TransactionMapper;
import com.example.ebank.models.Account;
import com.example.ebank.models.Customer;
import com.example.ebank.models.Transaction;
import com.example.ebank.models.TransactionRequest;
import com.example.ebank.services.AccountService;
import com.example.ebank.services.AsyncTransactionService;
import com.example.ebank.services.CustomerService;
import com.example.ebank.services.TransactionService;
import com.example.ebank.utils.KafkaServerProperties;
import com.example.ebank.utils.SecurityContextUtils;
import com.example.ebank.utils.logger.BFLogger;

import io.swagger.annotations.Api;

@Api(tags = "account")
@RestController
public class AccountController implements AccountApi {
    
    private final static String DATE_FORMAT = "yyyy-MM";
    private final static DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ofPattern(DATE_FORMAT))
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter();
    
    private final CustomerService customerService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;
    private final KafkaServerProperties kafkaProperties;
    private final AsyncTransactionService asyncTransactionService;
    private final SecurityContextUtils securityContextUtils;
    private final TransactionRequestProducer transactionRequestProducer;
    
    public AccountController(CustomerService customerService,
            AccountService accountService,
            AccountMapper accountMapper,
            TransactionService transactionService,
            TransactionMapper transactionMapper,
            KafkaServerProperties kafkaProperties,
            AsyncTransactionService asyncTransactionService,
            SecurityContextUtils securityContextUtils,
            TransactionRequestProducer transactionRequestProducer) {
        this.customerService = customerService;
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
        this.kafkaProperties = kafkaProperties;
        this.asyncTransactionService = asyncTransactionService;
        this.securityContextUtils = securityContextUtils;
        this.transactionRequestProducer = transactionRequestProducer;
    }
    
    @Override
    public ResponseEntity<List<AccountDto>> getCustomerAccounts(Long customerId) {
        return ResponseEntity.ok(accountMapper.toListDto(accountService.getByCustomer(customerId)));
    }
    
    @Override
    public ResponseEntity<AccountDto> getCustomerAccount(Long customerId, Long accountId) {
        Account account = accountService.getOne(accountId);
        validateAccessToRequestedCustomerAndAccount(customerId, account);
        return ResponseEntity.ok(accountMapper.toDto(account));
    }
    
    @Override
    public ResponseEntity<TransactionPageDto> getAccountTransactions(Long customerId,
            Long accountId,
            String dateString,
            Integer page,
            Integer size) {
        Account account = accountService.getOne(accountId);
        validateAccessToRequestedCustomerAndAccount(customerId, account);
        
        LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
        Page<Transaction> resultPage = Page.empty();
        
        if (kafkaProperties.readMockedTransactions()) {
            CompletableFuture<TransactionRequest> producer = transactionRequestProducer.send(customerId, accountId, date);
            CompletableFuture<Page<Transaction>> resultPageFuture = asyncTransactionService.findInMonthPaginated(date,
                    page, size);
            CompletableFuture.allOf(producer, resultPageFuture);
            try {
                resultPage = resultPageFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                BFLogger.logError("Error during reading mocked data from Kafka" + e.toString());
            }
        } else {
            resultPage = transactionService.findForAccountInMonthPaginated(accountId, date, page, size);
        }
        
        return ResponseEntity.ok(transactionMapper.toTransactionPageDto(resultPage));
    }
    
    private void validateAccessToRequestedCustomerAndAccount(Long customerId, Account account) {
        Customer customer = customerService.getOne(customerId);
        if (!Objects.equals(customer.getIdentityKey(), securityContextUtils.getIdentityKey())) {
            throw new IllegalArgumentException();
        }
        if (!Objects.equals(account.getCustomerId(), customer.getId())) {
            throw new IllegalArgumentException();
        }
    }
    
}
