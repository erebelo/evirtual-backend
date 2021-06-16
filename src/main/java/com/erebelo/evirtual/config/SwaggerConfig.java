package com.erebelo.evirtual.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI evirtualAPI() {
		Contact c = new Contact();
		c.setName("Eduardo Rebelo");
		c.setUrl("https://www.linkedin.com/in/eduardo-rebelo");

		return new OpenAPI().info(new Info().title("eVirtual backend").version("1.0.0").contact(c));
	}
}
