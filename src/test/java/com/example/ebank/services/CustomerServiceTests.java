package com.example.ebank.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.ebank.models.Customer;
import com.example.ebank.repositories.CustomerRepository;

@ExtendWith(SpringExtension.class)
public class CustomerServiceTests {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testGetAll_expectOk() {
        when(customerRepository.findAll()).thenReturn(getList());

        Iterable<Customer> result = customerService.getAll();
        assertThat(result).isNotEmpty()
            .hasSize(4);
    }

    @Test
    public void testGetOne_expectOk() {
        long id = 157L;
        when(customerRepository.findById(id))
            .thenReturn(Optional.of(getCustomer(id)));

        Customer result = customerService.getOne(id);

        assertThat(result).isNotNull();
    }

    @Test
    public void testGetOne_expectEntityNotFoundException() {
        long id = 157L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.getOne(id)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void testFindOneByIdentityKey_expectOk() {
        long id = 157L;
        String identityKey = "P-0157";
        when(customerRepository.findByIdAndIdentityKey(id, identityKey))
            .thenReturn(Optional.of(getCustomer(id)));

        Optional<Customer> result = customerService.findOneByIdentityKey(id, identityKey);

        assertThat(result).isPresent();
    }

    @Test
    public void testFindOneByIdentityKey_expectEmpty() {
        long id = 84L;
        String identityKey = "P-0157";
        when(customerRepository.findByIdAndIdentityKey(id, identityKey))
            .thenReturn(Optional.empty());

        Optional<Customer> result = customerService.findOneByIdentityKey(id, identityKey);

        assertThat(result).isNotPresent();
    }

    private List<Customer> getList() {
        return List.of(
            getCustomer(1L),
            getCustomer(2L),
            getCustomer(3L),
            getCustomer(4L));
    }

    private Customer getCustomer(Long id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setGivenName(randomString(10));
        customer.setFamilyName(randomString(30));
        customer.setIdentityKey(randomString(12));
        return customer;
    }

    static String randomString(int length) {
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
}
