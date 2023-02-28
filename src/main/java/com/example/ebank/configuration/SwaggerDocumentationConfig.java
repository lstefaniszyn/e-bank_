package com.example.ebank.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
@AllArgsConstructor
public class SwaggerDocumentationConfig {
    
    // How to use OpenAPI3.0 SwaggerUI https://springdoc.org/index.html#how-do-i-add-authorization-header-in-requests
    // Missing Cookie parameters and authorization in Swagger-UI https://github.com/swagger-api/swagger-js/issues/1163
    
    @Bean
    public OpenAPI customOpenAPI(OpenApiProperties properties) {
        return new OpenAPI()
                .info(getInfo(properties));
    }
    
    private Info getInfo(OpenApiProperties properties) {
        return new Info()
                .title(properties.getProjectTitle())
                .description(properties.getProjectDescription())
                .version(properties.getProjectVersion())
                .license(getLicense())
                .contact(properties.getContact());
    }
    
    private License getLicense() {
        return new License().name("Copyright 2023 BluePrint Company, all rights reserved");
    }
    
}