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

@Api(value = "customers", tags = "customer", description = "the customers API")

@RestController
@RequestMapping("/api/v1")
public class CustomerController {
    
    private final CustomerService customerService;
    
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    
    @ApiOperation(value = "Get all customers", nickname = "getCustomers", notes = "", response = Customer.class, responseContainer = "List", tags = {
            "customer", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Customer.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid client id supplied"),
            @ApiResponse(code = 404, message = "Customer not found") })
    @RequestMapping(value = "/customers", produces = { "application/json" }, method = RequestMethod.GET)
    public Iterable<Customer> getCustomers() {
        return customerService.getAll();
    }
    
    @ApiOperation(value = "Get customer by id", nickname = "getCustomerById", notes = "", response = Customer.class, tags = {
            "customer", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful operation", response = Customer.class),
            @ApiResponse(code = 400, message = "Invalid client id supplied"),
            @ApiResponse(code = 404, message = "Client not found") })
    @RequestMapping(value = "/customers/{idCustomer}", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomerById(
            @ApiParam(value = "The idCustomer that needs to be fetched. Use \"1\" for testing. ", required = true) @PathVariable Long idCustomer) {
        return ResponseEntity.ok(customerService.getOne(idCustomer));
    }
    
}
