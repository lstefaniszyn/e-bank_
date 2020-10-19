package com.example.ebank.repositories;

import com.example.ebank.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface JpaCustomerRepository extends CrudRepository<Customer, Long> {

}
