package com.erebelo.evirtual.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erebelo.evirtual.domain.Customer;
import com.erebelo.evirtual.repositories.CustomerRepository;
import com.erebelo.evirtual.services.exceptions.ObjectNotFoundException;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repo;

	public Customer find(Integer id) {
		Optional<Customer> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Object not found. Id: " + id + ", Class type: " + Customer.class.getName()));
	}
}
