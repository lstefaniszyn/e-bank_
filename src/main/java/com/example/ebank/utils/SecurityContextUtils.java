package com.example.ebank.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextUtils {

    public String getIdentityKey() {
        return (String) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();
    }
}
