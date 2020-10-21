package com.example.ebank.controllers;

import java.util.List;

import com.example.ebank.models.Account;
import com.example.ebank.models.Customer;
import com.example.ebank.services.AccountService;
import com.example.ebank.services.CustomerService;

import org.springframework.data.domain.Page;
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

@Api(value = "accounts", tags = "account", description = "the accounts API")
@RestController
@RequestMapping("/api/v1")
public class AccountController {

        private final CustomerService customerService;
        private final AccountService accountService;

        public AccountController(CustomerService customerService, AccountService accountService) {
                this.customerService = customerService;
                this.accountService = accountService;
        }

        @ApiOperation(value = "Get customers's accounts", nickname = "list", notes = "", response = Account.class, responseContainer = "List", tags = {
                        "account", })
        @ApiResponses(value = {
                        @ApiResponse(code = 200, message = "successful operation", response = Account.class, responseContainer = "List"),
                        @ApiResponse(code = 400, message = "Invalid customer id supplied"),
                        @ApiResponse(code = 404, message = "Customer not found") })
        @RequestMapping(value = "/accounts", produces = { "application/json" }, method = RequestMethod.GET)
        public Iterable<Account> list() {
                return accountService.getAll();
        }

        @ApiOperation(value = "Get customer's accounts", nickname = "get", notes = "", response = Account.class, responseContainer = "List", tags = {
                        "account", })
        @ApiResponses(value = {
                        @ApiResponse(code = 200, message = "successful operation", response = Account.class, responseContainer = "List"),
                        @ApiResponse(code = 400, message = "Invalid customer id supplied"),
                        @ApiResponse(code = 404, message = "Customer not found") })
        @RequestMapping(value = "/accounts/{id}", produces = { "application/json" }, method = RequestMethod.GET)
        public ResponseEntity<Account> get(
                        @ApiParam(value = "The id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long id) {
                return ResponseEntity.ok(accountService.getOne(id));
        }

        @ApiOperation(value = "Get accounts for given customer", nickname = "get", notes = "", response = Account.class, tags = {
                        "customer", })
        @ApiResponses(value = { @ApiResponse(code = 200, message = "successful operation", response = Account.class),
                        @ApiResponse(code = 400, message = "Invalid client id supplied"),
                        @ApiResponse(code = 404, message = "customer not found") })
        @RequestMapping(value = "/customers/{id}/accounts", produces = { "application/json" }, method = RequestMethod.GET)
        public ResponseEntity<List<Account>> getAccounts(
                        @ApiParam(value = "The id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long id,
                        @ApiParam(value = "The Page number to fetched. Use \"0\" for testing. ", required = false) @RequestParam(name = "page", defaultValue = "0") int page,
                        @ApiParam(value = "The number of objects fetch. Use \"2\" for testing. ", required = false) @RequestParam(name = "size", defaultValue = "2") int size) {
                Page<Account> accounts = accountService.getForCustomer(id, page, size);
                return ResponseEntity.ok(accounts.getContent());
        }

        @ApiOperation(value = "Get account details for given customer", nickname = "get", notes = "", response = Account.class, tags = {
                        "customer", })
        @ApiResponses(value = { @ApiResponse(code = 200, message = "successful operation", response = Account.class),
                        @ApiResponse(code = 400, message = "Invalid client id supplied"),
                        @ApiResponse(code = 404, message = "Client not found") })
        @RequestMapping(value = "/customers/{id}/accounts/{accountId}", produces = {
                        "application/json" }, method = RequestMethod.GET)
        public ResponseEntity<Account> getAccount(
                        @ApiParam(value = "The id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long id,
                        @ApiParam(value = "The account id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long accountId,
                        @ApiParam(value = "The Page number to fetched. Use \"0\" for testing. ", required = false) @RequestParam(name = "page", defaultValue = "0") int page,
                        @ApiParam(value = "The number of objects fetch. Use \"2\" for testing. ", required = false) @RequestParam(name = "size", defaultValue = "2") int size) {

                Customer customer = customerService.getOne(id);
                Account account = accountService.getOne(accountId);
                if (!account.getCustomer().getId().equals(customer.getId())) {
                        throw new IllegalArgumentException();
                }
                return ResponseEntity.ok(account);
        }

}
