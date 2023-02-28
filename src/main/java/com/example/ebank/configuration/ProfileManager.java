package com.example.ebank.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ProfileManager {

    @Value("${spring.profiles.active}")
    private String activeProfiles;

    public List<String> getActiveProfiles() {
        return Arrays.asList(activeProfiles.split(","));
    }

    public boolean isMockProfileActive() {
        return Arrays.asList(activeProfiles.split(",")).contains("mock");
    }
}