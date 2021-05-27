package com.erebelo.evirtual.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erebelo.evirtual.domain.Address;
import com.erebelo.evirtual.domain.BilletPayment;
import com.erebelo.evirtual.domain.CardPayment;
import com.erebelo.evirtual.domain.Category;
import com.erebelo.evirtual.domain.City;
import com.erebelo.evirtual.domain.Customer;
import com.erebelo.evirtual.domain.CustomerOrder;
import com.erebelo.evirtual.domain.CustomerOrderItem;
import com.erebelo.evirtual.domain.Payment;
import com.erebelo.evirtual.domain.Product;
import com.erebelo.evirtual.domain.State;
import com.erebelo.evirtual.domain.enums.CustomerType;
import com.erebelo.evirtual.domain.enums.PaymentStatus;
import com.erebelo.evirtual.repositories.AddressRepository;
import com.erebelo.evirtual.repositories.CategoryRepository;
import com.erebelo.evirtual.repositories.CityRepository;
import com.erebelo.evirtual.repositories.CustomerOrderItemRepository;
import com.erebelo.evirtual.repositories.CustomerOrderRepository;
import com.erebelo.evirtual.repositories.CustomerRepository;
import com.erebelo.evirtual.repositories.PaymentRepository;
import com.erebelo.evirtual.repositories.ProductRepository;
import com.erebelo.evirtual.repositories.StateRepository;

@Service
public class DBService {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CustomerOrderRepository customerOrderRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private CustomerOrderItemRepository customerOrderItemRepository;

	public void instantiateTestDatabse() throws ParseException {
		/*
		 * Category and Product
		 */
		// Creating categories
		Category cat1 = new Category(null, "Computing");
		Category cat2 = new Category(null, "Office");
		Category cat3 = new Category(null, "Bed, Table and Bath");
		Category cat4 = new Category(null, "Eletronics");
		Category cat5 = new Category(null, "Gardening");
		Category cat6 = new Category(null, "Decoration");
		Category cat7 = new Category(null, "Perfumery");

		// Creating products
		Product p1 = new Product(null, "Computer", 2000.00);
		Product p2 = new Product(null, "Printer", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		Product p4 = new Product(null, "Office table", 300.00);
		Product p5 = new Product(null, "Towel", 50.00);
		Product p6 = new Product(null, "Quilt", 200.00);
		Product p7 = new Product(null, "Gamer monitor", 1200.00);
		Product p8 = new Product(null, "Brushcutter", 800.00);
		Product p9 = new Product(null, "Bedside lamp", 100.00);
		Product p10 = new Product(null, "Comb", 180.00);
		Product p11 = new Product(null, "Shampoo", 90.00);

		// Associating the categories and products
		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2, p4));
		cat3.getProducts().addAll(Arrays.asList(p5, p6));
		cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProducts().addAll(Arrays.asList(p8));
		cat6.getProducts().addAll(Arrays.asList(p9));
		cat7.getProducts().addAll(Arrays.asList(p10, p11));

		p1.getCategories().addAll(Arrays.asList(cat1, cat4));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategories().addAll(Arrays.asList(cat1, cat4));
		p4.getCategories().addAll(Arrays.asList(cat2));
		p5.getCategories().addAll(Arrays.asList(cat3));
		p6.getCategories().addAll(Arrays.asList(cat3));
		p7.getCategories().addAll(Arrays.asList(cat4));
		p8.getCategories().addAll(Arrays.asList(cat5));
		p9.getCategories().addAll(Arrays.asList(cat6));
		p10.getCategories().addAll(Arrays.asList(cat6));
		p11.getCategories().addAll(Arrays.asList(cat7));

		// Inserting the categories and products
		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		/*
		 * State and City
		 */
		// Creating states
		State s1 = new State(null, "Minas Gerais");
		State s2 = new State(null, "São Paulo");

		// Creating cities
		City city1 = new City(null, "Uberlândia", s1);
		City city2 = new City(null, "São Paulo", s2);
		City city3 = new City(null, "Campinas", s2);

		// Associating the states and cities
		s1.getCities().addAll(Arrays.asList(city1));
		s2.getCities().addAll(Arrays.asList(city2, city3));

		// Inserting the states and cities
		stateRepository.saveAll(Arrays.asList(s1, s2));
		cityRepository.saveAll(Arrays.asList(city1, city2, city3));

		/*
		 * Customer and Address
		 */
		// Creating customers
		Customer c1 = new Customer(null, "Maria Silva", "maria@gmail.com", "29892749303", CustomerType.NATURALPERSON);
		c1.getPhones().addAll(Arrays.asList("31977451180", "11987358801"));

		// Creating addresses
		Address addr1 = new Address(null, "Rua Flores", "300", "Apt 303", "Jardins", "15749444", c1, city1);
		Address addr2 = new Address(null, "Avenida Principal", "100", "Sala 8", "Centro", "15846741", c1, city2);

		// Associating the customers and addresses
		c1.getAddresses().addAll(Arrays.asList(addr1, addr2));

		// Inserting the customers and addresses
		customerRepository.saveAll(Arrays.asList(c1));
		addressRepository.saveAll(Arrays.asList(addr1, addr2));

		/*
		 * Customer Order and Payment
		 */
		// Creating customer orders
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		CustomerOrder order1 = new CustomerOrder(null, sdf.parse("01/02/2020 14:11"), c1, addr1);
		CustomerOrder order2 = new CustomerOrder(null, sdf.parse("12/03/2021 09:30"), c1, addr2);

		// Creating payments
		Payment pay1 = new CardPayment(null, PaymentStatus.PAIDOFF, order1, 6);
		Payment pay2 = new BilletPayment(null, PaymentStatus.PENDING, order2, sdf.parse("17/03/2021 00:00"), null);

		// Associating the customer orders and payments
		order1.setPayment(pay1);
		order2.setPayment(pay2);

		// Associating the customer and customer orders
		c1.getCustomerOrders().addAll(Arrays.asList(order1, order2));

		// Inserting the customer orders and payments
		customerOrderRepository.saveAll(Arrays.asList(order1, order2));
		paymentRepository.saveAll(Arrays.asList(pay1, pay2));

		/*
		 * Customer Order Item
		 */
		// Creating customer order items
		CustomerOrderItem orderItem1 = new CustomerOrderItem(order1, p1, 0.00, 1, 2000.00);
		CustomerOrderItem orderItem2 = new CustomerOrderItem(order1, p3, 0.00, 2, 80.00);
		CustomerOrderItem orderItem3 = new CustomerOrderItem(order2, p2, 100.00, 1, 800.00);

		// Associating the customer orders and customer order items
		order1.getItems().addAll(Arrays.asList(orderItem1, orderItem2));
		order2.getItems().addAll(Arrays.asList(orderItem3));

		// Associating the products and customer order items
		p1.getItems().addAll(Arrays.asList(orderItem1));
		p2.getItems().addAll(Arrays.asList(orderItem3));
		p3.getItems().addAll(Arrays.asList(orderItem2));

		// Inserting the customer order items
		customerOrderItemRepository.saveAll(Arrays.asList(orderItem1, orderItem2, orderItem3));
	}
}
