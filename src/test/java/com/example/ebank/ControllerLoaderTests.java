package com.example.ebank;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.ebank.controllers.AccountController;
import com.example.ebank.controllers.CustomerController;
import com.example.ebank.controllers.TransactionController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EBankApplication.class)
public class ControllerLoaderTests {

	@Autowired
	private AccountController accountController;

	@Autowired
	private CustomerController customerController;

	@Autowired
	private TransactionController transactionController;

	@Test
	public void contextLoadsAccountController() throws Exception {
		assertThat(accountController).isNotNull();
	}

	@Test
	public void contextLoadsCustomController() throws Exception {
		assertThat(customerController).isNotNull();
	}

	@Test
	public void contextLoadsTransactionController() throws Exception {
		assertThat(transactionController).isNotNull();
	}
}
