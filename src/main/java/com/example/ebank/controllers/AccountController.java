package com.example.ebank.controllers;

import com.example.ebank.generated.api.AccountApi;
import com.example.ebank.generated.dto.AccountDto;
import com.example.ebank.generated.dto.TransactionDto;
import com.example.ebank.mappers.AccountMapper;
import com.example.ebank.mappers.TransactionMapper;
import com.example.ebank.models.Account;
import com.example.ebank.models.Customer;
import com.example.ebank.models.Transaction;
import com.example.ebank.services.AccountService;
import com.example.ebank.services.AsyncTransactionService;
import com.example.ebank.services.CustomerService;
import com.example.ebank.services.TransactionService;
import com.example.ebank.utils.KafkaServerProperties;
import com.example.ebank.utils.SecurityContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class AccountController implements AccountApi {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final static String DATE_FORMAT = "yyyy-MM";
    private final static DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
        .append(DateTimeFormatter.ofPattern(DATE_FORMAT)).parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
        .toFormatter();

    private final CustomerService customerService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;
    private final KafkaServerProperties kafkaProperties;
    private final AsyncTransactionService asyncTransactionService;

    public AccountController(CustomerService customerService,
        AccountService accountService,
        AccountMapper accountMapper,
        TransactionService transactionService,
        TransactionMapper transactionMapper,
        KafkaServerProperties kafkaProperties,
        AsyncTransactionService asyncTransactionService) {
        this.customerService = customerService;
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
        this.kafkaProperties = kafkaProperties;
        this.asyncTransactionService = asyncTransactionService;
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
    public ResponseEntity<List<TransactionDto>> getAccountTransactions(Long customerId, Long accountId, String dateString, Integer page, Integer size) {
        Account account = accountService.getOne(accountId);
        validateAccessToRequestedCustomerAndAccount(customerId, account);

        LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
        Page<Transaction> resultPage = Page.empty();

        if (kafkaProperties.readMockedTransactions()) {
            CompletableFuture<Page<Transaction>> resultPageFuture = asyncTransactionService.findInMonthPaginated(date, page, size);
            CompletableFuture.allOf(resultPageFuture);
            try {
                resultPage = resultPageFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Error during reading mocked data from Kafka", e);
            }
        } else {
            resultPage = transactionService.findForAccountInMonthPaginated(accountId, date, page,
                size);
        }

        return ResponseEntity.ok(transactionMapper.toListDto(resultPage.getContent()));
    }

    private void validateAccessToRequestedCustomerAndAccount(Long customerId, Account account) {
        Customer customer = customerService.getOne(customerId);
        if (!Objects.equals(customer.getIdentityKey(), SecurityContextUtils.getIdentityKey())) {
            throw new IllegalArgumentException();
        }
        if (!Objects.equals(account.getCustomer().getId(), customer.getId())) {
            throw new IllegalArgumentException();
        }
    }

}
