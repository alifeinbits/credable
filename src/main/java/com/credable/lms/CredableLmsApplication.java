package com.credable.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class CredableLmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CredableLmsApplication.class, args);
	}

}
