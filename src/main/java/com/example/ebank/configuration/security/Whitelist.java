package com.example.ebank.configuration.security;

import io.vavr.control.Option;

import static io.vavr.collection.Stream.of;

public class Whitelist {

    private static final String[] CONFIGURATION_API = {
            "/",
            "/v3/api-docs.*",
            "/swagger-ui.*",
            "/actuator.*",
            "/api-docs.*",
            "/webjars.*",
            "/swagger-resources.*"
    };
    private static final String[] APPLICATION_API = {
            "/*/api"
    };

    // Created to resolve issue with mutable string list
    public static String[] getConfigurationAPI() {
        return CONFIGURATION_API.clone();
    }

    // Created to resolve issue with mutable string list
    public static String[] getApplicationAPI() {
        return APPLICATION_API.clone();
    }

    public static boolean isWhitelisted(String path) {
        Option<String> whitelistConfigAPI = of(CONFIGURATION_API).filter(c -> path.matches(c)).toOption();
        Option<String> whitelistApplicationAPI = of(APPLICATION_API).filter(c -> path.matches(c)).toOption();
        return !whitelistConfigAPI.isEmpty() || !whitelistApplicationAPI.isEmpty();
    }

}