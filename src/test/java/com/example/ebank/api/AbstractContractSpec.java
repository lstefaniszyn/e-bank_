package com.example.ebank.api;

import com.example.ebank.controllers.AppStatusController;

import org.junit.Before;
import org.mockito.InjectMocks;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

public class AbstractContractSpec {
    
    @InjectMocks
    AppStatusController appStatusController;
    
    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(appStatusController);
    }
    
}
