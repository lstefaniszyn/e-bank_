package com.example.ebank.controllers;

import com.example.ebank.models.Customer;
import com.example.ebank.services.CustomerService;
import com.example.ebank.utils.SecurityContextUtils;
import org.springframework.security.access.AccessDeniedException;

public abstract class BasicController {

    private final CustomerService customerService;
    private final SecurityContextUtils securityContextUtils;

    public BasicController(CustomerService customerService,
        SecurityContextUtils securityContextUtils) {
        this.customerService = customerService;
        this.securityContextUtils = securityContextUtils;
    }

    protected void validateAccessToRequestedCustomer(Long customerId) {
        getCustomerOrAccessDenied(customerId);
    }

    protected Customer getValidatedCustomer(Long customerId) {
        return getCustomerOrAccessDenied(customerId);
    }

    private Customer getCustomerOrAccessDenied(Long customerId) {
        return customerService.findOneByIdentityKey(customerId, securityContextUtils.getIdentityKey())
            .orElseThrow(() -> new AccessDeniedException("Access to requested customer denied"));
    }
}
