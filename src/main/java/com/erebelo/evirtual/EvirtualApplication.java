package com.erebelo.evirtual;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.erebelo.evirtual.domain.Category;
import com.erebelo.evirtual.repositories.CategoryRepository;

@SpringBootApplication
public class EvirtualApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(EvirtualApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Inserting categories
		Category cat1 = new Category(null, "Computing");
		Category cat2 = new Category(null, "Office");

		categoryRepository.saveAll(Arrays.asList(cat1, cat2));
	}
}
