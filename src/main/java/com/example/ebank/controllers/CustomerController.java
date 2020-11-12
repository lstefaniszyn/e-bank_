package com.example.ebank.controllers;

import com.example.ebank.generated.api.CustomerApi;
import com.example.ebank.generated.dto.CustomerDto;
import com.example.ebank.generated.dto.CustomerListItemDto;
import com.example.ebank.mappers.CustomerMapper;
import com.example.ebank.models.Customer;
import com.example.ebank.services.CustomerService;
import com.example.ebank.utils.SecurityContextUtils;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Api(tags = "customer")
@RestController
public class CustomerController implements CustomerApi {
    
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final SecurityContextUtils securityContextUtils;
    
    public CustomerController(CustomerService customerService,
            CustomerMapper customerMapper, SecurityContextUtils securityContextUtils) {
        
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.securityContextUtils = securityContextUtils;
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
        if (!Objects.equals(customer.getIdentityKey(), securityContextUtils.getIdentityKey())) {
            throw new IllegalArgumentException();
        }
    }
}
