package com.erebelo.evirtual.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.erebelo.evirtual.domain.Customer;
import com.erebelo.evirtual.domain.CustomerOrder;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Integer> {

	@Transactional(readOnly = true)
	Page<CustomerOrder> findByCustomer(Customer customer, Pageable pageRequest);
}
