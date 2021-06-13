package com.erebelo.evirtual.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erebelo.evirtual.domain.BilletPayment;
import com.erebelo.evirtual.domain.Customer;
import com.erebelo.evirtual.domain.CustomerOrder;
import com.erebelo.evirtual.domain.CustomerOrderItem;
import com.erebelo.evirtual.domain.enums.PaymentStatus;
import com.erebelo.evirtual.domain.enums.Profile;
import com.erebelo.evirtual.repositories.CustomerOrderItemRepository;
import com.erebelo.evirtual.repositories.CustomerOrderRepository;
import com.erebelo.evirtual.repositories.PaymentRepository;
import com.erebelo.evirtual.security.UserSpringSecurity;
import com.erebelo.evirtual.services.email.EmailService;
import com.erebelo.evirtual.services.exceptions.AuthorizationException;
import com.erebelo.evirtual.services.exceptions.ObjectNotFoundException;
import com.erebelo.evirtual.services.security.UserService;

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

	@Autowired
	private CustomerService customerService;

	@Autowired
	private EmailService emaliService;

	public CustomerOrder find(Integer id) {
		// Getting the user logged in
		UserSpringSecurity user = UserService.authenticated();

		if (user != null) {
			// Getting the customer order
			Optional<CustomerOrder> customerOrder = repo.findById(id);
			if (user.hasRole(Profile.ADMIN)) {
				return customerOrder.orElseThrow(() -> new ObjectNotFoundException(
						"Object not found. Id: " + id + ", Class type: " + CustomerOrder.class.getName()));
			} else {
				// Getting the customer from the customer order
				Customer customer = customerService.find(user.getId());

				if (customerOrder.isPresent()) {
					// Checking if the user has permission to access
					if (customer.getId().equals(customerOrder.get().getCustomer().getId())) {
						return customerOrder.get();
					} else {
						throw new AuthorizationException("Access denied");
					}
				} else {
					throw new ObjectNotFoundException(
							"Object not found. Id: " + id + ", Class type: " + CustomerOrder.class.getName());
				}
			}
		} else {
			throw new AuthorizationException("Access denied");
		}
	}

	@Transactional
	public CustomerOrder insert(CustomerOrder obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.setCustomer(customerService.find(obj.getCustomer().getId()));
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
			item.setProduct(productService.find(item.getProduct().getId()));
			item.setPrice(item.getProduct().getPrice());
			item.setCustomerOrder(obj);
		}
		// Saving the order items
		customerOrderItemRepository.saveAll(obj.getItems());

		// Sending email
		emaliService.sendOrderConfirmationHtmlEmail(obj);

		return obj;
	}

	public Page<CustomerOrder> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSpringSecurity user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Access denied");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Customer customer = customerService.find(user.getId());
		return repo.findByCustomer(customer, pageRequest);
	}
}
