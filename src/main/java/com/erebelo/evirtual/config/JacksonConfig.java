package com.erebelo.evirtual.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.erebelo.evirtual.domain.BilletPayment;
import com.erebelo.evirtual.domain.CardPayment;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfig {

	// https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(BilletPayment.class);
				objectMapper.registerSubtypes(CardPayment.class);
				super.configure(objectMapper);
			}
		};
		return builder;
	}
}
