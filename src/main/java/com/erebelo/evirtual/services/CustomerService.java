package com.erebelo.evirtual.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.erebelo.evirtual.domain.Address;
import com.erebelo.evirtual.domain.City;
import com.erebelo.evirtual.domain.Customer;
import com.erebelo.evirtual.domain.enums.CustomerType;
import com.erebelo.evirtual.dto.CustomerDTO;
import com.erebelo.evirtual.dto.CustomerNewDTO;
import com.erebelo.evirtual.repositories.AddressRepository;
import com.erebelo.evirtual.repositories.CustomerRepository;
import com.erebelo.evirtual.services.exceptions.DataIntegrityException;
import com.erebelo.evirtual.services.exceptions.ObjectNotFoundException;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repo;

	@Autowired
	private AddressRepository addressRepository;

	public Customer find(Integer id) {
		Optional<Customer> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Object not found. Id: " + id + ", Class type: " + Customer.class.getName()));
	}

	@Transactional
	public Customer insert(Customer obj) {
		obj.setId(null);
		obj = repo.save(obj);
		addressRepository.saveAll(obj.getAddresses());
		return obj;
	}

	public Customer update(Customer obj) {
		// Checking if the object id is not null and treating the concurrency problem
		Customer newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		// Checking if the id is not null
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("You cannot remove it because there are related entities");
		}
	}

	public List<Customer> findAll() {
		return repo.findAll();
	}

	public Page<Customer> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Customer fromDTO(CustomerDTO objDto) {
		return new Customer(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null);
	}

	public Customer fromDTO(CustomerNewDTO objDto) {
		Customer c = new Customer(null, objDto.getName(), objDto.getEmail(), objDto.getSsnOrNrle(),
				CustomerType.toEnum(objDto.getType()));
		City city = new City(objDto.getCityId(), null, null);
		Address addr = new Address(null, objDto.getStreetAddress(), objDto.getNumber(), objDto.getComplement(),
				objDto.getDistrict(), objDto.getZipCode(), c, city);
		c.getAddresses().add(addr);
		c.getPhones().add(objDto.getPhone1());
		if (objDto.getPhone2() != null) {
			c.getPhones().add(objDto.getPhone2());
		}
		if (objDto.getPhone3() != null) {
			c.getPhones().add(objDto.getPhone3());
		}
		return c;
	}

	private void updateData(Customer newObj, Customer obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
}
