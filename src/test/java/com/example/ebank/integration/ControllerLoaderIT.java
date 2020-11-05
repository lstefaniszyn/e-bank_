package com.example.ebank.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.ebank.controllers.AccountController;
import com.example.ebank.controllers.CustomerController;
import com.example.ebank.controllers.TransactionController;
import com.example.ebank.integration.serenity.SerenityReportBase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ControllerLoaderIT extends SerenityReportBase {

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
