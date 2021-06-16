package com.erebelo.evirtual.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.erebelo.evirtual.security.JWTAuthenticationFilter;
import com.erebelo.evirtual.security.JWTAuthorizationFilter;
import com.erebelo.evirtual.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JWTUtil jwtUtil;

	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**", "/v3/api-docs", "/swagger-ui.html", "/swagger-ui/**",
			"/v3/api-docs/swagger-config" };
	private static final String[] PUBLIC_MATCHERS_GET = { "/products/**", "/categories/**" };
	private static final String[] PUBLIC_MATCHERS_POST = { "/customers/**", "/auth/password-recovery/**" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Allowing H2 Console access to the test environment
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}

		// Activating the corsConfigurationSource method through http.cors() code
		// Disabling the CSRF (authentication and session storage) attack protection as
		// this system is stateless and doesn't store this information
		http.cors().and().csrf().disable();
		http.authorizeRequests()//
				.antMatchers(PUBLIC_MATCHERS).permitAll()//
				.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()//
				.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()//
				.anyRequest().authenticated();
		// Setting the filter for authentication
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		// Setting the filter for authorization
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

	// Setting the instance able to get the user by email and the encrypt algorithm
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	// Allowing the endpoint access by multiple sources with basic configurations
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
