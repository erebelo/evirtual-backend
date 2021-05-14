package com.erebelo.evirtual;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.erebelo.evirtual.domain.Category;
import com.erebelo.evirtual.domain.City;
import com.erebelo.evirtual.domain.Product;
import com.erebelo.evirtual.domain.State;
import com.erebelo.evirtual.repositories.CategoryRepository;
import com.erebelo.evirtual.repositories.CityRepository;
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
		City c1 = new City(null, "Uberlândia", s1);
		City c2 = new City(null, "São Paulo", s2);
		City c3 = new City(null, "Campinas", s2);

		// Associating the states and cities
		s1.getCities().addAll(Arrays.asList(c1));
		s2.getCities().addAll(Arrays.asList(c2, c3));

		// Inserting the states and cities
		stateRepository.saveAll(Arrays.asList(s1, s2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));
	}
}
