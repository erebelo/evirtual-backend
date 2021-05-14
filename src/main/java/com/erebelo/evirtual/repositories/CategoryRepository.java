package com.erebelo.evirtual.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erebelo.evirtual.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
