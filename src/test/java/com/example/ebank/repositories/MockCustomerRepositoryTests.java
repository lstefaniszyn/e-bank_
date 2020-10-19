package com.example.ebank.repositories;

import com.example.ebank.models.Customer;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MockCustomerRepositoryTests {

    private final MockCustomerRepository customerRepository = new MockCustomerRepository();

    @Test
    public void testFindAllCustomers() {
        Set<Customer> result = customerRepository.findAll();

        assertThat(result).isNotEmpty();
    }

    @Test
    public void testFindCustomerById_expectPresent() {
        Optional<Customer> result = customerRepository.findById(2L);

        assertThat(result).isNotEmpty();
    }

    @Test
    public void testFindCustomerById_expectEmpty() {
        Optional<Customer> result = customerRepository.findById(2598451L);

        assertThat(result).isEmpty();
    }
}
