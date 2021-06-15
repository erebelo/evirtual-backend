package com.erebelo.evirtual.services.security;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.erebelo.evirtual.domain.Customer;
import com.erebelo.evirtual.repositories.CustomerRepository;
import com.erebelo.evirtual.services.email.EmailService;
import com.erebelo.evirtual.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private EmailService emailService;

	private Random random = new Random();

	public void sendNewPassword(String email) {

		Customer customer = customerRepository.findByEmail(email);
		if (customer == null) {
			throw new ObjectNotFoundException("Email not found");
		}

		String newPassword = newPassword();
		customer.setPassword(bCryptPasswordEncoder.encode(newPassword));

		customerRepository.save(customer);
		emailService.sendPasswordRecoveryHtmlEmail(customer, newPassword);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		// See the unicode table for reference
		if (opt == 0) { // Generating a numeric digit
			return (char) (random.nextInt(10) + 48);
		} else if (opt == 1) { // Generating a capital letter
			return (char) (random.nextInt(26) + 65);
		} else { // Generating a lowercase letter
			return (char) (random.nextInt(26) + 97);
		}
	}
}
