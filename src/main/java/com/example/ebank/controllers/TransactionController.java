package com.example.ebank.controllers;

import com.example.ebank.models.Account;
import com.example.ebank.models.Customer;
import com.example.ebank.models.Transaction;
import com.example.ebank.services.AccountService;
import com.example.ebank.services.CustomerService;
import com.example.ebank.services.TransactionService;
import com.example.ebank.utils.SecurityContextUtils;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;

@Api(value = "transactions", tags = "transaction", description = "the transaction API")
@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    
    private final static String DATE_FORMAT = "yyyy-MM";
    private final static DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ofPattern(DATE_FORMAT))
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter();
    
    private final CustomerService customerService;
    private final TransactionService transactionService;
    private final AccountService accountService;
    
    public TransactionController(CustomerService customerService, TransactionService transactionService,
            AccountService accountService) {
        
        this.customerService = customerService;
        this.transactionService = transactionService;
        this.accountService = accountService;
    }
    
    @ApiOperation(value = "Get account's transactions", nickname = "getAllByDatePaginated", notes = "", response = Transaction.class, responseContainer = "List", tags = {
            "transaction", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Transaction.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid client id supplied"),
            @ApiResponse(code = 404, message = "Client not found") })
    @RequestMapping(value = "/transactions", produces = { "application/json" }, method = RequestMethod.GET)
    public List<Transaction> getAllByDatePaginated(
            @ApiParam(value = "The date to filter. Use \"2020-10\" for testing. ", required = true) @RequestParam(name = "date") @DateTimeFormat(pattern = DATE_FORMAT) String dateString,
            @ApiParam(value = "The Page number to fetched. Use \"0\" for testing. ", required = false) @RequestParam(name = "page", defaultValue = "0") int page,
            @ApiParam(value = "The number of objects fetch. Use \"2\" for testing. ", required = false) @RequestParam(name = "size", defaultValue = "2") int size) {
        LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
        Page<Transaction> resultsPage = transactionService.findInMonthPaginated(date, page, size);
        return resultsPage.getContent();
    }
    
    @ApiOperation(value = "Get transactions for given customer and account and date", nickname = "getAccountTransactions", notes = "", response = Transaction.class, tags = {
            "transaction", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Transaction.class),
            @ApiResponse(code = 400, message = "Invalid client id supplied"),
            @ApiResponse(code = 404, message = "customer not found") })
    @RequestMapping(value = "/customers/{idCustomer}/accounts/{idAccount}/transactions", produces = {
            "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<List<Transaction>> getAccountTransactions(
            @ApiParam(value = "The idCustomer that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long idCustomer,
            @ApiParam(value = "The idAccount that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long idAccount,
            @ApiParam(value = "The date to filter. Use \"2020-10\" for testing. ", required = true) @RequestParam(name = "date") @DateTimeFormat(pattern = DATE_FORMAT) String dateString,
            @ApiParam(value = "The Page number to fetched. Use \"0\" for testing. ", required = false) @RequestParam(name = "page", defaultValue = "0") int page,
            @ApiParam(value = "The number of objects fetch. Use \"2\" for testing. ", required = false) @RequestParam(name = "size", defaultValue = "2") int size) {
        
        Customer customer = customerService.getByIdentityKey(SecurityContextUtils.getIdentityKey());
        Account account = accountService.getOne(idAccount);
        if (!account.getCustomer()
                .getId()
                .equals(customer.getId())) {
            throw new IllegalArgumentException();
        }
        
        LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
        Page<Transaction> resultPage = transactionService.findForAccountInMonthPaginated(
                idAccount, date, page,
                size);
        
        return ResponseEntity.ok(resultPage.getContent());
    }
}
