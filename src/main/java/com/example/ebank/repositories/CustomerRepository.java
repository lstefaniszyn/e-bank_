package com.example.ebank.repositories;

import java.util.List;
import java.util.Optional;

import com.example.ebank.models.Customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    
    @Override
    List<Customer> findAll();
    
    Optional<Customer> findByIdentityKey(String identityKey);
    
}
