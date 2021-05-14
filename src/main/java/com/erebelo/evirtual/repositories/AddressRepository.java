package com.erebelo.evirtual.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erebelo.evirtual.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
