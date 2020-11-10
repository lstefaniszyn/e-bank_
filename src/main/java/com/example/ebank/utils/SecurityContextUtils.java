package com.example.ebank.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtils {
    
    public String getIdentityKey() {
        return (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
