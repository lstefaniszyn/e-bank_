package com.example.ebank.controllers;

import com.example.ebank.models.Account;
import com.example.ebank.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.*;

@Api(value = "accounts", description = "the accounts API")
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

        private final AccountService accountService;

        public AccountController(AccountService accountService) {
                this.accountService = accountService;
        }

        @ApiOperation(value = "Get customers's accounts", nickname = "list", notes = "", response = Account.class, responseContainer = "List", tags = {
                        "account", })
        @ApiResponses(value = {
                        @ApiResponse(code = 200, message = "successful operation", response = Account.class, responseContainer = "List"),
                        @ApiResponse(code = 400, message = "Invalid customer id supplied"),
                        @ApiResponse(code = 404, message = "Customer not found") })
        @RequestMapping(value = "/", produces = { "application/json" }, method = RequestMethod.GET)
        public Iterable<Account> list() {
                return accountService.getAll();
        }

        @ApiOperation(value = "Get customer's accounts", nickname = "get", notes = "", response = Account.class, responseContainer = "List", tags = {
                        "account", })
        @ApiResponses(value = {
                        @ApiResponse(code = 200, message = "successful operation", response = Account.class, responseContainer = "List"),
                        @ApiResponse(code = 400, message = "Invalid customer id supplied"),
                        @ApiResponse(code = 404, message = "Customer not found") })
        @RequestMapping(value = "/{id}", produces = { "application/json" }, method = RequestMethod.GET)
        public ResponseEntity<Account> get(
                        @ApiParam(value = "The id that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long id) {
                return ResponseEntity.ok(accountService.getOne(id));
        }

}
