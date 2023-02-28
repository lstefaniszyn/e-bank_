package com.example.ebank.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.Annotation;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import io.swagger.v3.oas.models.info.Contact;

@ConfigurationProperties(prefix = "openapi")
@ConstructorBinding
@AllArgsConstructor
@Getter
public class OpenApiProperties {
    
    // in application.properties project-
    private final String projectTitle;
    private final String projectDescription;
    private final String projectVersion;
    
    // in application.properties contact-
    private final String contactName;
    private final String contactUrl;
    private final String contactEmail;
    
    public Contact getContact() {
        Contact contact = new Contact();
        contact.setName(this.contactName);
        contact.setUrl(this.contactUrl);
        contact.setEmail(this.contactEmail);
        return contact;
        
    }
}