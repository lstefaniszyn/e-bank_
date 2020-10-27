package com.example.ebank.repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.ebank.models.Account;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MockAccountRepository {

    private static final Logger logger = LoggerFactory.getLogger(MockAccountRepository.class);

    public List<Account> findAll() {
        return getAccounts();
    }

    public Optional<Account> findById(Long id) {
        return getAccounts().stream().filter(c -> Objects.equals(c.getId(), id)).findFirst();
    }
    
    public Page<Account> findByCustomerId(Long customerId, Pageable pageable) {
    	List<Account> allAccounts = findAll().stream()
                .filter(a -> a.getCustomer().getId().equals(customerId))
                .collect(Collectors.toList());
    	return getPagedTransactions(pageable, allAccounts);
    }
    
    private Page<Account> getPagedTransactions(Pageable pageable, List<Account> allAccounts) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int from = pageNumber * pageSize;
        int to = (pageNumber + 1) * pageSize;
        int accountsSize = allAccounts.size();
        List<Account> accounts = from <= accountsSize
                ? to <= accountsSize
                ? allAccounts.subList(from, to)
                : allAccounts.subList(from, accountsSize)
                : List.of();
        return new PageImpl<>(accounts, pageable, allAccounts.size());
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
