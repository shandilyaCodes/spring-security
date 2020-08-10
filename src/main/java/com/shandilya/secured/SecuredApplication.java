package com.shandilya.secured;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@EnableOAuth2Sso
@SpringBootApplication
public class SecuredApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuredApplication.class, args);
	}

}