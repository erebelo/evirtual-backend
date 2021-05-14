package com.erebelo.evirtual.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erebelo.evirtual.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

}
