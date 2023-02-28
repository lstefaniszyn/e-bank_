package com.example.ebank.configuration.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class ExampleAuthenticationProvider implements AuthenticationProvider {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String customToken = (String) authentication.getCredentials();
        
        // To validate cookie value
        if (isValidToken(customToken)) {
            authentication.setAuthenticated(true);
        }
        
        return authentication;
    }
    
    private boolean isValidToken(String customToken) {
        
        // !!! Next Azure Board task to call auth service to check validity of token. If needed
        boolean isValid = true;
        log.info("isValidToken(). Used token:" + customToken);
        
        // keeping boolean flag for simplicity
        return isValid;
        
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        // Lets use inbuilt token class for simplicity
        return PreAuthenticatedAuthenticationToken.class.equals(authentication);
    }
    
}