package com.erebelo.evirtual.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erebelo.evirtual.domain.BilletPayment;
import com.erebelo.evirtual.domain.CustomerOrder;
import com.erebelo.evirtual.domain.CustomerOrderItem;
import com.erebelo.evirtual.domain.enums.PaymentStatus;
import com.erebelo.evirtual.repositories.CustomerOrderItemRepository;
import com.erebelo.evirtual.repositories.CustomerOrderRepository;
import com.erebelo.evirtual.repositories.PaymentRepository;
import com.erebelo.evirtual.services.exceptions.ObjectNotFoundException;

@Service
public class CustomerOrderService {

	@Autowired
	private CustomerOrderRepository repo;

	@Autowired
	private BilletService billetService;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerOrderItemRepository customerOrderItemRepository;

	public CustomerOrder find(Integer id) {
		Optional<CustomerOrder> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found. Id: " + id + ", Class type: " + CustomerOrder.class.getName()));
	}

	@Transactional
	public CustomerOrder insert(CustomerOrder obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.getPayment().setPaymentStatus(PaymentStatus.PENDING);
		// Setting the customer order object on payment object
		obj.getPayment().setCustomerOrder(obj);

		// Setting the due date to billet payment type
		if (obj.getPayment() instanceof BilletPayment) {
			BilletPayment pay = (BilletPayment) obj.getPayment();
			billetService.fillBilletPayment(pay, obj.getInstant());
		}
		// Saving customer order and payment
		obj = repo.save(obj);
		paymentRepository.save(obj.getPayment());

		// Building the order item objects and setting the customer order object
		for (CustomerOrderItem item : obj.getItems()) {
			item.setDiscount(0.0);
			// Setting the price through product object
			item.setPrice(productService.find(item.getProduct().getId()).getPrice());
			item.setCustomerOrder(obj);
		}
		// Saving the order items
		customerOrderItemRepository.saveAll(obj.getItems());
		return obj;
	}
}
