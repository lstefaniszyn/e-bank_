package com.example.ebank.repositories;

import com.example.ebank.models.Customer;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MockCustomerRepositoryTests {

	private final MockCustomerRepository customerRepository = new MockCustomerRepository();

	@Test
	public void testFindAllCustomers() {
		List<Customer> result = customerRepository.findAll();

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

	@Test
	public void testFindCustomerByIdentityKey_expectPresent() {
		Optional<Customer> result = customerRepository.findByIdentityKey("P-01");

		assertThat(result).isNotEmpty();
	}

	@Test
	public void testFindCustomerByIdentityKey_expectEmpty() {
		Optional<Customer> result = customerRepository.findByIdentityKey("Nonexistent key");

		assertThat(result).isEmpty();
	}
}
