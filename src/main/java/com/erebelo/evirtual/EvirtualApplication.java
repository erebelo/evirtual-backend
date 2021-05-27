package com.erebelo.evirtual;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EvirtualApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EvirtualApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
