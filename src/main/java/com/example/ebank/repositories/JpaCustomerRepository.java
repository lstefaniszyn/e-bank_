package com.example.ebank.repositories;

import com.example.ebank.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JpaCustomerRepository extends CrudRepository<Customer, Long> {

	Optional<Customer> findByIdentityKey(String identityKey);

}
