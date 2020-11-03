package com.example.ebank.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ebank.models.Account;
import com.example.ebank.models.Customer;
import com.example.ebank.models.Transaction;
import com.example.ebank.services.AccountService;
import com.example.ebank.services.AsyncTransactionService;
import com.example.ebank.services.CustomerService;
import com.example.ebank.services.TransactionService;
import com.example.ebank.utils.KafkaServerProperties;
import com.example.ebank.utils.SecurityContextUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@Api(value = "transactions", tags = "transaction", description = "the transaction API")
@RestController
@RequestMapping("/api/v1")
public class TransactionController {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
	
	private final static String DATE_FORMAT = "yyyy-MM";
	private final static DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
			.append(DateTimeFormatter.ofPattern(DATE_FORMAT))
			.parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
			.toFormatter();
	
	private final CustomerService customerService;
	private final TransactionService transactionService;
	private final AccountService accountService;
	private final KafkaServerProperties kafkaProperties;
	private final AsyncTransactionService asyncTransactionService;
	
	public TransactionController(CustomerService customerService, TransactionService transactionService,
			AccountService accountService, KafkaServerProperties kafkaProperties,
			AsyncTransactionService asyncTransactionService) {
		
		this.customerService = customerService;
		this.transactionService = transactionService;
		this.accountService = accountService;
		this.kafkaProperties = kafkaProperties;
		this.asyncTransactionService = asyncTransactionService;
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
	
	@ApiOperation(value = "Get transactions for given customer and account and date", nickname = "get", notes = "", response = Transaction.class, tags = {
			"transaction", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "successful operation", response = Transaction.class),
			@ApiResponse(code = 400, message = "Invalid client id supplied"),
			@ApiResponse(code = 404, message = "customer not found") })
	@RequestMapping(value = "customers/{id}/accounts/{accountId}/transactions", produces = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<Transaction>> getTransactions(
			@ApiParam(value = "The customer id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long id,
			@ApiParam(value = "The account id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long accountId,
			@ApiParam(value = "The date to filter. Use \"2020-10\" for testing. ", required = true) @RequestParam(name = "date") @DateTimeFormat(pattern = DATE_FORMAT) String dateString,
			@ApiParam(value = "The Page number to fetched. Use \"0\" for testing. ", required = false) @RequestParam(name = "page", defaultValue = "0") int page,
			@ApiParam(value = "The number of objects fetch. Use \"2\" for testing. ", required = false) @RequestParam(name = "size", defaultValue = "2") int size) {
		
		Customer customer = customerService.getOne(id);
		Account account = accountService.getOne(accountId);
		if (!account.getCustomer()
				.getId()
				.equals(customer.getId())) {
			throw new IllegalArgumentException();
		}
		
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
		
		return ResponseEntity.ok(resultPage.getContent());
	}
	
	@ApiOperation(value = "Get transactions for given account and date secured", nickname = "get", notes = "", response = Transaction.class, authorizations = {
			@Authorization(value = "bearerAuth") }, tags = { "transaction", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "successful operation", response = Transaction.class),
			@ApiResponse(code = 400, message = "Invalid client id supplied"),
			@ApiResponse(code = 401, message = "Access token is missing or invalid"),
			@ApiResponse(code = 404, message = "account not found") })
	@RequestMapping(value = "/accounts/{accountId}/transactions", produces = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<Transaction>> getTransactionsSecured(
			@ApiParam(value = "The account id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long accountId,
			@ApiParam(value = "The date to filter. Use \"2020-10\" for testing. ", required = true) @RequestParam(name = "date") @DateTimeFormat(pattern = DATE_FORMAT) String dateString,
			@ApiParam(value = "The Page number to fetched. Use \"0\" for testing. ", required = false) @RequestParam(name = "page", defaultValue = "0") int page,
			@ApiParam(value = "The number of objects fetch. Use \"2\" for testing. ", required = false) @RequestParam(name = "size", defaultValue = "2") int size) {
		
		Customer customer = customerService.getByIdentityKey(SecurityContextUtils.getIdentityKey());
		
		Account account = accountService.getOne(accountId);
		if (!account.getCustomer()
				.getId()
				.equals(customer.getId())) {
			throw new IllegalArgumentException();
		}
		
		LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
		Page<Transaction> resultPage = transactionService.findForAccountInMonthPaginated(accountId, date, page,
				size);
		
		return ResponseEntity.ok(resultPage.getContent());
	}
}
