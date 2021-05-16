package com.erebelo.evirtual.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erebelo.evirtual.domain.CustomerOrder;
import com.erebelo.evirtual.repositories.CustomerOrderRepository;
import com.erebelo.evirtual.services.exceptions.ObjectNotFoundException;

@Service
public class CustomerOrderService {

	@Autowired
	private CustomerOrderRepository repo;

	public CustomerOrder find(Integer id) {
		Optional<CustomerOrder> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found. Id: " + id + ", Class type: " + CustomerOrder.class.getName()));
	}
}
