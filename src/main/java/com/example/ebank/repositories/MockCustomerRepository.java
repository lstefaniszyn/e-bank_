package com.example.ebank.repositories;

import com.example.ebank.models.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class MockCustomerRepository {

	private static final Logger logger = LoggerFactory.getLogger(MockCustomerRepository.class);

	public List<Customer> findAll() {
		return getCustomers();
	}

	public Optional<Customer> findById(Long id) {
		return getCustomers().stream().filter(c -> Objects.equals(c.getId(), id)).findFirst();
	}

	public Optional<Customer> findByIdentityKey(String identityKey) {
		return getCustomers().stream().filter(c -> Objects.equals(c.getIdentityKey(), identityKey)).findFirst();
	}

	private List<Customer> getCustomers() {
		List<Customer> customers = new ArrayList<>();
		Resource resource = new ClassPathResource("data/customers.json");
		try {
			File file = resource.getFile();
			ObjectMapper jsonMapper = new ObjectMapper();
			customers = jsonMapper.readValue(file, new TypeReference<List<Customer>>() {
			});
		} catch (IOException exc) {
			logger.warn("IOException occurred during loading mock collection of customers.");
		}
		logger.info("Loaded mock collection of customers.");
		return customers;
	}
}
