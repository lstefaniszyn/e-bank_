package com.example.ebank.configuration.security;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestValidationFilter extends OncePerRequestFilter {
    
    AuthenticationManager authenticationManager;
    
    public RequestValidationFilter(@Lazy AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Create a token object to pass to Authentication Provider. 
        // This implementation is prepare to use in next step of application. 
        // Currently it is always true, but in next feature we will implement depends what kind of authorization will be required
        String creds = "someCreds";
        Authentication token = new PreAuthenticatedAuthenticationToken("Bearer", creds);
        Authentication authenticate = authenticationManager.authenticate(token);

        // Save user principle in security context
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return Whitelist.isWhitelisted(path);
    }
}