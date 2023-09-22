package com.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RegistrationAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationAppApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplateBean() {
		return new RestTemplate();
	}

}
