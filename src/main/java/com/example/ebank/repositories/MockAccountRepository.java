package com.example.ebank.repositories;

import com.example.ebank.models.Account;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MockAccountRepository {

	private static final Logger logger = LoggerFactory.getLogger(MockAccountRepository.class);

	public List<Account> findAll() {
		return getAccounts();
	}

	public Optional<Account> findById(Long id) {
		return getAccounts().stream().filter(c -> Objects.equals(c.getId(), id)).findFirst();
	}

	public List<Account> findByCustomerId(Long customerId) {
		return findAll().stream()
				.filter(a -> a.getCustomer().getId().equals(customerId))
				.collect(Collectors.toList());
	}

	private List<Account> getAccounts() {
		List<Account> accounts = new ArrayList<>();
		Resource resource = new ClassPathResource("data/accounts.json");
		try {
			File file = resource.getFile();
			ObjectMapper jsonMapper = new ObjectMapper();
			accounts = jsonMapper.readValue(file, new TypeReference<List<Account>>() {
			});
		} catch (IOException exc) {
			logger.warn("IOException occurred during loading mock collection of accounts.");
		}
		logger.info("Loaded mock collection of accounts.");
		return accounts;
	}

}
