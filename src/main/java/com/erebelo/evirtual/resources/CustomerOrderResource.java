package com.erebelo.evirtual.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.erebelo.evirtual.domain.Category;
import com.erebelo.evirtual.domain.CustomerOrder;
import com.erebelo.evirtual.dto.CategoryDTO;
import com.erebelo.evirtual.services.CustomerOrderService;

@RestController
@RequestMapping(value = "/customer-orders")
public class CustomerOrderResource {

	@Autowired
	private CustomerOrderService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<CustomerOrder> find(@PathVariable Integer id) {
		CustomerOrder obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CustomerOrder obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
