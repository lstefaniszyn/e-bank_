package com.example.ebank.repositories;

import com.example.ebank.models.Transaction;
import com.example.ebank.utils.logger.BFLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MockTransactionRepository {

    public Optional<Transaction> findById(Long id) {
        return getTransactions().stream().filter(c -> Objects.equals(c.getId(), id)).findFirst();
    }

    public Page<Transaction> findAll(Pageable pageable) {
        return getPagedTransactions(pageable, getTransactions());
    }

    public Page<Transaction> findByValueDateBetween(Date startDate, Date endDate, Pageable pageable) {
        List<Transaction> allTransactions = getTransactions().stream()
            .filter(t -> isInPeriod(t.getDate(), startDate, endDate)).collect(Collectors.toList());
        return getPagedTransactions(pageable, allTransactions);
    }

    private Page<Transaction> getPagedTransactions(Pageable pageable, List<Transaction> allTransactions) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int from = pageNumber * pageSize;
        int to = (pageNumber + 1) * pageSize;
        int transactionsSize = allTransactions.size();
        Collections.sort(allTransactions, (t1, t2) -> t1.getDate().compareTo(t2.getDate()));
        List<Transaction> transactions = from <= transactionsSize
            ? to <= transactionsSize ? allTransactions.subList(from, to)
            : allTransactions.subList(from, transactionsSize)
            : List.of();
        return new PageImpl<>(transactions, pageable, allTransactions.size());
    }

    public Page<Transaction> findByValueDateBetweenAndAccountId(Date startDate, Date endDate, Long accountId,
        Pageable pageable) {
        return findByValueDateBetween(startDate, endDate, pageable);
    }

    private boolean isInPeriod(Date date, Date start, Date end) {
        return date.compareTo(start) >= 0 && date.compareTo(end) <= 0;
    }

    private List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        Resource resource = new ClassPathResource("data/transactions_1_1.json");
        try {
            File file = resource.getFile();
            ObjectMapper jsonMapper = new ObjectMapper();
            transactions = jsonMapper.readValue(file, new TypeReference<List<Transaction>>() {
            });
        } catch (IOException exc) {
            BFLogger.logWarn("IOException occurred during loading mock collection of transactions.");
        }
        BFLogger.logInfo("Loaded mock collection of transactions.");
        return transactions;
    }

}
