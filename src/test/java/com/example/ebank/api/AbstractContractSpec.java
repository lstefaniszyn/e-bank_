package com.example.ebank.api;

import static org.mockito.BDDMockito.given;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

import com.capgemini.mrchecker.test.core.logger.BFLogger;
import com.example.ebank.controllers.AppStatusController;
import com.example.ebank.controllers.CustomerController;
import com.example.ebank.models.Customer;
import com.example.ebank.services.CustomerService;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(MockitoJUnitRunner.class)
public class AbstractContractSpec {
    
    @Mock
    CustomerService customerService;
    
    @InjectMocks
    CustomerController customerController;
    
    @InjectMocks
    AppStatusController appStatusController;
    
    @Before
    public void setup() {
        BFLogger.logDebug("!!!! TEST ME !!!");
        given(customerService.getOne(1L)).willReturn(getCustomer(1L));
        given(customerService.getAll()).willReturn(getCustomers());
        RestAssuredMockMvc.standaloneSetup(appStatusController, customerController);
    }
    
    private List<Customer> getCustomers() {
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
        customer.setIdentityKey(randomNumber(12));
        return customer;
    }
    
    static String randomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }
    
    static String randomNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }
}
