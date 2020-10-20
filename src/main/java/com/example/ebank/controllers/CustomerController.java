package com.example.ebank.controllers;

import com.example.ebank.models.Account;
import com.example.ebank.models.Customer;
import com.example.ebank.models.Transaction;
import com.example.ebank.services.AccountService;
import com.example.ebank.services.CustomerService;
import com.example.ebank.services.TransactionService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "clients", description = "the clients API")

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

	private final static String DATE_FORMAT = "yyyy-MM";
	private final static DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
			.append(DateTimeFormatter.ofPattern(DATE_FORMAT)).parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
			.toFormatter();

	private final CustomerService customerService;
	private final TransactionService transactionService;
	private final AccountService accountService;

	public CustomerController(CustomerService customerService, TransactionService transactionService,
			AccountService accountService) {

		this.customerService = customerService;
		this.transactionService = transactionService;
		this.accountService = accountService;
	}

	@ApiOperation(value = "Get all clients", nickname = "list", notes = "", response = Customer.class, responseContainer = "List", tags = {
			"client", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "successful operation", response = Customer.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Invalid client id supplied"),
			@ApiResponse(code = 404, message = "Client not found") })
	@RequestMapping(value = "/", produces = { "application/json" }, method = RequestMethod.GET)
	public Iterable<Customer> list() {
		return customerService.getAll();
	}

	@ApiOperation(value = "Get client by id", nickname = "get", notes = "", response = Customer.class, tags = {
			"client", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful operation", response = Customer.class),
			@ApiResponse(code = 400, message = "Invalid client id supplied"),
			@ApiResponse(code = 404, message = "Client not found") })
	@RequestMapping(value = "/{id}", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Customer> get(
			@ApiParam(value = "The id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long id) {
		return ResponseEntity.ok(customerService.getOne(id));
	}
	
	@ApiOperation(value = "Get accounts for given user", nickname = "get", notes = "", response = Account.class, tags = {
			"client", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful operation", response = Account.class),
			@ApiResponse(code = 400, message = "Invalid client id supplied"),
			@ApiResponse(code = 404, message = "Client not found") })
	@RequestMapping(value = "/{id}/accounts", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<Account>> getAccounts(
			@ApiParam(value = "The id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long id,
			@ApiParam(value = "The Page number to fetched. Use \"0\" for testing. ", required = false) @RequestParam(name = "page", defaultValue = "0") int page,
			@ApiParam(value = "The number of objects fetch. Use \"2\" for testing. ", required = false) @RequestParam(name = "size", defaultValue = "2") int size){
		Page<Account> accounts = accountService.getForCustomer(id, page, size);
		return ResponseEntity.ok(accounts.getContent());
	}
	
	@ApiOperation(value = "Get account details for given user", nickname = "get", notes = "", response = Account.class, tags = {
			"client", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful operation", response = Account.class),
			@ApiResponse(code = 400, message = "Invalid client id supplied"),
			@ApiResponse(code = 404, message = "Client not found") })
	@RequestMapping(value = "/{id}/accounts/{accountId}", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Account> getAccount(
			@ApiParam(value = "The id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long id,
			@ApiParam(value = "The account id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long accountId,
			@ApiParam(value = "The Page number to fetched. Use \"0\" for testing. ", required = false) @RequestParam(name = "page", defaultValue = "0") int page,
			@ApiParam(value = "The number of objects fetch. Use \"2\" for testing. ", required = false) @RequestParam(name = "size", defaultValue = "2") int size){
		
		Customer customer = customerService.getOne(id);
		Account account = accountService.getOne(accountId);
		if (!account.getCustomer().getId().equals(customer.getId())) {
			throw new IllegalArgumentException();
		}
		return ResponseEntity.ok(account);
	}
	

	@ApiOperation(value = "Get transactions for given user and account and date", nickname = "get", notes = "", response = Transaction.class, tags = {
			"client", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful operation", response = Transaction.class),
			@ApiResponse(code = 400, message = "Invalid client id supplied"),
			@ApiResponse(code = 404, message = "Client not found") })
	@RequestMapping(value = "/{id}/accounts/{accountId}/transactions", produces = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<Transaction>> getTransactions(
			@ApiParam(value = "The customer id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long id,
			@ApiParam(value = "The account id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long accountId,
			@ApiParam(value = "The date to filter. Use \"2020-10\" for testing. ", required = true) @RequestParam(name = "date") @DateTimeFormat(pattern = DATE_FORMAT) String dateString,
			@ApiParam(value = "The Page number to fetched. Use \"0\" for testing. ", required = false) @RequestParam(name = "page", defaultValue = "0") int page,
			@ApiParam(value = "The number of objects fetch. Use \"2\" for testing. ", required = false) @RequestParam(name = "size", defaultValue = "2") int size) {

		Customer customer = customerService.getOne(id);
		Account account = accountService.getOne(accountId);
		if (!account.getCustomer().getId().equals(customer.getId())) {
			throw new IllegalArgumentException();
		}

		LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
		Page<Transaction> resultPage = transactionService.findForAccountInMonthPaginated(accountId, date, page, size);

		return ResponseEntity.ok(resultPage.getContent());
	}

}
