package com.erebelo.evirtual.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erebelo.evirtual.domain.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

}
