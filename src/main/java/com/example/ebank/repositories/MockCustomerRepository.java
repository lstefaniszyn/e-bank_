package com.example.ebank.repositories;

import com.example.ebank.models.Customer;
import com.example.ebank.utils.logger.BFLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class MockCustomerRepository {

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
            BFLogger.logWarn("IOException occurred during loading mock collection of customers.");
        }
        BFLogger.logInfo("Loaded mock collection of customers.");
        return customers;
    }
}
