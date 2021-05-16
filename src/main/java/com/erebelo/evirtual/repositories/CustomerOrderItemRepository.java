package com.erebelo.evirtual.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erebelo.evirtual.domain.CustomerOrderItem;

@Repository
public interface CustomerOrderItemRepository extends JpaRepository<CustomerOrderItem, Integer> {

}
