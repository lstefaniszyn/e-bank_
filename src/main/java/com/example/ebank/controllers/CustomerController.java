package com.example.ebank.controllers;

import com.example.ebank.models.Customer;
import com.example.ebank.services.CustomerService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

        private final CustomerService customerService;

        public CustomerController(CustomerService customerService) {
                this.customerService = customerService;
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

}
