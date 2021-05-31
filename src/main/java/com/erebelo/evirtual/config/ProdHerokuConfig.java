package com.erebelo.evirtual.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.erebelo.evirtual.services.email.EmailService;
import com.erebelo.evirtual.services.email.SmtpEmailService;

@Configuration
@Profile("prodheroku")
public class ProdHerokuConfig {

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
