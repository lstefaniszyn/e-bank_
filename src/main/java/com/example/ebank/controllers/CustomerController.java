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

@Api(value = "customers", tags = "customer", description = "the customers API")

@RestController
@RequestMapping("/api/v1")
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

	@ApiOperation(value = "Get all customers", nickname = "list", notes = "", response = Customer.class, responseContainer = "List", tags = {
			"customer", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "successful operation", response = Customer.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Invalid client id supplied"),
			@ApiResponse(code = 404, message = "Customer not found") })
	@RequestMapping(value = "/customers", produces = { "application/json" }, method = RequestMethod.GET)
	public Iterable<Customer> list() {
		return customerService.getAll();
	}

	@ApiOperation(value = "Get customer by id", nickname = "get", notes = "", response = Customer.class, tags = {
			"customer", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful operation", response = Customer.class),
			@ApiResponse(code = 400, message = "Invalid client id supplied"),
			@ApiResponse(code = 404, message = "Client not found") })
	@RequestMapping(value = "/customers/{id}", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Customer> get(
			@ApiParam(value = "The id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long id) {
		return ResponseEntity.ok(customerService.getOne(id));
	}
	
	
	

	

}
