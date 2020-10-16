package com.example.ebank;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ebank.controllers.AccountController;
import com.example.ebank.controllers.CustomerController;
import com.example.ebank.controllers.TransactionController;


@SpringBootTest
class ControllerLoaderTests {
	
	@Autowired
	private AccountController accountController;
	
	@Autowired
	private CustomerController customerController;
	
	@Autowired
	private TransactionController transactionController;
	
	@Test
	void contextLoadsAccountController() throws Exception {
		assertThat(accountController).isNotNull();
	}
	
	@Test
	void contextLoadsCustomController() throws Exception {
		assertThat(customerController).isNotNull();
	}
	
	@Test
	void contextLoadsTransactionController() throws Exception {
		assertThat(transactionController).isNotNull();
	}
}
