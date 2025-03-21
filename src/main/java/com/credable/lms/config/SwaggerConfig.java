package com.credable.lms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Loan Management System API")
                        .version("1.0.0").
                        contact(new Contact()
                                .name("Felix Mwanza")
                                .email("felix.mutua@credable.io")
                                .url("https://credable.io"))
                                .license(new License()
                                        .name("MIT License")
                                        .url("https://opensource.org/licenses/MIT"))
                        .description("This project implements a Loan Management System that integrates with a Bank's Core Banking System (CBS) and a Scoring Engine to provide micro-loan products to bank customers.\n" +
                                "\n"));
    }
}

