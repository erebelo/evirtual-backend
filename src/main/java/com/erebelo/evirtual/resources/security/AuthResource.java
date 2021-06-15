package com.erebelo.evirtual.resources.security;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.erebelo.evirtual.dto.security.EmailDTO;
import com.erebelo.evirtual.security.JWTUtil;
import com.erebelo.evirtual.security.UserSpringSecurity;
import com.erebelo.evirtual.services.security.AuthService;
import com.erebelo.evirtual.services.security.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthService service;

	// Authorization: logged in
	@RequestMapping(value = "/refresh-token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSpringSecurity user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}

	// Authorization free
	@RequestMapping(value = "/password-recovery", method = RequestMethod.POST)
	public ResponseEntity<Void> passwordRecovery(@Valid @RequestBody EmailDTO objDto) {
		service.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}
}
