package com.erebelo.evirtual;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.erebelo.evirtual.domain.Category;
import com.erebelo.evirtual.domain.Product;
import com.erebelo.evirtual.repositories.CategoryRepository;
import com.erebelo.evirtual.repositories.ProductRepository;

@SpringBootApplication
public class EvirtualApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(EvirtualApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
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
	}
}
