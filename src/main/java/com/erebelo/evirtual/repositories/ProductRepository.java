package com.erebelo.evirtual.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erebelo.evirtual.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
