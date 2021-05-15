package com.erebelo.evirtual.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.erebelo.evirtual.domain.Customer;
import com.erebelo.evirtual.services.CustomerService;

@RestController
@RequestMapping(value = "/customer")
public class CustomerResource {

	@Autowired
	private CustomerService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Customer obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
}
