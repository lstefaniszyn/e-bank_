package com.example.ebank.services;

import com.example.ebank.models.Customer;
import com.example.ebank.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Iterable<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findOne(Long id) {
        return customerRepository.findById(id);
    }
}
