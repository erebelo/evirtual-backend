package com.erebelo.evirtual;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.erebelo.evirtual.domain.Address;
import com.erebelo.evirtual.domain.BilletPayment;
import com.erebelo.evirtual.domain.CardPayment;
import com.erebelo.evirtual.domain.Category;
import com.erebelo.evirtual.domain.City;
import com.erebelo.evirtual.domain.Customer;
import com.erebelo.evirtual.domain.CustomerOrder;
import com.erebelo.evirtual.domain.Payment;
import com.erebelo.evirtual.domain.Product;
import com.erebelo.evirtual.domain.State;
import com.erebelo.evirtual.domain.enums.CustomerType;
import com.erebelo.evirtual.domain.enums.PaymentStatus;
import com.erebelo.evirtual.repositories.AddressRepository;
import com.erebelo.evirtual.repositories.CategoryRepository;
import com.erebelo.evirtual.repositories.CityRepository;
import com.erebelo.evirtual.repositories.CustomerOrderRepository;
import com.erebelo.evirtual.repositories.CustomerRepository;
import com.erebelo.evirtual.repositories.PaymentRepository;
import com.erebelo.evirtual.repositories.ProductRepository;
import com.erebelo.evirtual.repositories.StateRepository;

@SpringBootApplication
public class EvirtualApplication implements CommandLineRunner {

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

	public static void main(String[] args) {
		SpringApplication.run(EvirtualApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		 * Category and Product
		 */
		// Creating categories
		Category cat1 = new Category(null, "Computing");
		Category cat2 = new Category(null, "Office");

		// Creating products
		Product p1 = new Product(null, "Computer", 2000.00);
		Product p2 = new Product(null, "Printer", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);

		// Associating the categories and products
		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2));

		p1.getCategories().addAll(Arrays.asList(cat1));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().addAll(Arrays.asList(cat1));

		// Inserting the categories and products
		categoryRepository.saveAll(Arrays.asList(cat1, cat2));
		productRepository.saveAll(Arrays.asList(p1, p2, p3));

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
	}
}
