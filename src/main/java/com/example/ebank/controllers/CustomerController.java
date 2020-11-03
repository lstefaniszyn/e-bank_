package com.example.ebank.controllers;

import com.example.ebank.mappers.CustomerMapper;
import com.example.ebank.models.Customer;
import com.example.ebank.services.CustomerService;
import com.example.ebank.utils.SecurityContextUtils;
import org.openapitools.api.CustomerApi;
import org.openapitools.dto.CustomerDto;
import org.openapitools.dto.CustomerListItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerService customerService,
        CustomerMapper customerMapper) {

        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @Override
    public ResponseEntity<List<CustomerListItemDto>> getCustomers() {
        return ResponseEntity.ok(customerMapper.toListDto(customerService.getAll()));
    }

    @Override
    public ResponseEntity<CustomerDto> getCustomerById(Long id) {
        Customer customer = customerService.getOne(id);
        validateAccessToRequestedCustomer(customer);
        return ResponseEntity.ok(customerMapper.toDto(customer));
    }

    private void validateAccessToRequestedCustomer(Customer customer) {
        if (!Objects.equals(customer.getIdentityKey(), SecurityContextUtils.getIdentityKey())) {
            throw new IllegalArgumentException();
        }
    }
}
