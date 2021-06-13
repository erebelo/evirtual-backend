package com.erebelo.evirtual.services.security;

import org.springframework.security.core.context.SecurityContextHolder;

import com.erebelo.evirtual.security.UserSpringSecurity;

// Default service for security implementation that returns the logged in user 
public class UserService {

	public static UserSpringSecurity authenticated() {
		try {
			return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
