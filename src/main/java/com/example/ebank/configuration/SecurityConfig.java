package com.example.ebank.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import com.example.ebank.configuration.security.ExampleAuthenticationProvider;
import com.example.ebank.configuration.security.PostAuthenticationLoggingFilter;
import com.example.ebank.configuration.security.RequestValidationFilter;
import com.example.ebank.configuration.security.Whitelist;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private ExampleAuthenticationProvider exampleAuthenticationProvider;
    
    @Autowired
    private PostAuthenticationLoggingFilter postAuthenticationLoggingFilter;
    
    @Autowired
    private RequestValidationFilter requestValidatorFilter;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .disable() // No Http Basic Login
                .csrf()
                .disable() // No CSRF token
                .formLogin()
                .disable() // No Form Login
                .logout()
                .disable() // No Logout
                // No Session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .and()
                // Authorization process
                .authenticationProvider(exampleAuthenticationProvider)
                // action before authorization
                .addFilterBefore(requestValidatorFilter, AnonymousAuthenticationFilter.class)
                // action after authorization
                .addFilterAfter(postAuthenticationLoggingFilter, AnonymousAuthenticationFilter.class)
                // Authorize requests
                .authorizeRequests()
                .regexMatchers(Whitelist.getConfigurationAPI())
                .permitAll()
                .regexMatchers(Whitelist.getApplicationAPI())
                .permitAll()
                .anyRequest()
                .authenticated();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(exampleAuthenticationProvider);
    }
    
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    
}