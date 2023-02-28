package com.example.ebank.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HomeControllerTest {
    
    private HomeController underTest;
    
    @Test
    public void testHomeController(){
        String expectedRedirect = "redirect:swagger-ui/index.html";
        underTest = new HomeController();
        String home = underTest.index();

        Assertions.assertEquals(expectedRedirect, home);
    }
}