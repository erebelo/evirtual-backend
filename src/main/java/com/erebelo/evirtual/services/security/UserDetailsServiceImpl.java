package com.erebelo.evirtual.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.erebelo.evirtual.domain.Customer;
import com.erebelo.evirtual.repositories.CustomerRepository;
import com.erebelo.evirtual.security.UserSpringSecurity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	CustomerRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer c1 = repo.findByEmail(username);
		if (c1 == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserSpringSecurity(c1.getId(), c1.getEmail(), c1.getPassword(), c1.getProfiles());
	}
}
