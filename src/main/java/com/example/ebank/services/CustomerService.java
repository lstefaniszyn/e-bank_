package com.example.ebank.services;

import com.example.ebank.models.Customer;
import com.example.ebank.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getOne(Long id) {
        return customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Optional<Customer> findOneByIdentityKey(Long id, String identityKey) {
        return customerRepository.findByIdAndIdentityKey(id, identityKey);
    }
}
