package com.erebelo.evirtual.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.erebelo.evirtual.services.DBService;
import com.erebelo.evirtual.services.email.EmailService;
import com.erebelo.evirtual.services.email.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabse();
		return true;
	}

	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
