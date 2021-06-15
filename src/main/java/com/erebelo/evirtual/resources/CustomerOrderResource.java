package com.erebelo.evirtual.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.erebelo.evirtual.domain.CustomerOrder;
import com.erebelo.evirtual.services.CustomerOrderService;

@RestController
@RequestMapping(value = "/customer-orders")
public class CustomerOrderResource {

	@Autowired
	private CustomerOrderService service;

	// Authorization: logged in
	// Roles: ADMIN gets all. CUSTOMER gets only their orders.
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<CustomerOrder> find(@PathVariable Integer id) {
		CustomerOrder obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	// Authorization: logged in
	// Roles: ADMIN posts for everyone. CUSTOMER only posts for itself.
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CustomerOrder obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	// Authorization: logged in
	// Roles: ADMIN and CUSTOMER get only their orders.
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<CustomerOrder>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "instant") String orderBy,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		Page<CustomerOrder> list = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}
}
