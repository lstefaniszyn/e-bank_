package com.example.ebank.controllers;

import com.example.ebank.autogenerated.api.CustomerApi;
import com.example.ebank.autogenerated.dto.CustomerDto;
import com.example.ebank.autogenerated.dto.CustomerListItemDto;
import com.example.ebank.mappers.CustomerMapper;
import com.example.ebank.models.Customer;
import com.example.ebank.services.CustomerService;
import com.example.ebank.utils.SecurityContextUtils;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController extends BasicController implements CustomerApi {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerService customerService,
        CustomerMapper customerMapper,
        SecurityContextUtils securityContextUtils) {
        super(customerService, securityContextUtils);
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @Override
    public ResponseEntity<List<CustomerListItemDto>> getCustomers() {
        return ResponseEntity.ok(customerMapper.toListDto(customerService.getAll()));
    }

    @Override
    public ResponseEntity<CustomerDto> getCustomerById(Long id) {
        Customer customer = getValidatedCustomer(id);
        return ResponseEntity.ok(customerMapper.toDto(customer));
    }
    
}
