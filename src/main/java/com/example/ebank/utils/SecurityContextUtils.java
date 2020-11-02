package com.example.ebank.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtils {

	public static String getIdentityKey() {
		return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
