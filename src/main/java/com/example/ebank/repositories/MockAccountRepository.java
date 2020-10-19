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
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Component
public class MockAccountRepository {

    private static final Logger logger = LoggerFactory.getLogger(MockAccountRepository.class);

    public Set<Account> findAll() {
        return getAccounts();
    }

    public Optional<Account> findById(Long id) {
        return getAccounts().stream().filter(c -> Objects.equals(c.getId(), id)).findFirst();
    }

    private Set<Account> getAccounts() {
        Set<Account> accounts = new HashSet<>();
        Resource resource = new ClassPathResource("data/accounts.json");
        try {
            File file = resource.getFile();
            ObjectMapper jsonMapper = new ObjectMapper();
            accounts = jsonMapper.readValue(file, new TypeReference<Set<Account>>() {
            });
        } catch (IOException exc) {
            logger.warn("IOException occurred during loading mock collection of accounts.");
        }
        logger.info("Loaded mock collection of accounts.");
        return accounts;
    }

}
