package com.erebelo.evirtual.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;

	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**" };
	private static final String[] PUBLIC_MATCHERS_GET = { "/products/**", "/categories/**" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Allowing H2 Console access to the test environment
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}

		// Activating the CorsConfigurationSource defined
		// Disabling the CSRF (authentication and session storage) attack protection as
		// this system is stateless and doesn't store this information
		http.cors().and().csrf().disable();
		http.authorizeRequests()//
				.antMatchers(PUBLIC_MATCHERS).permitAll()//
				.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()//
				.anyRequest().authenticated();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

	// Allowing the endpoint access by multiple sources with basic configurations
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}
