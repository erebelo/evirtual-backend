package com.erebelo.evirtual.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.erebelo.evirtual.domain.Address;
import com.erebelo.evirtual.domain.City;
import com.erebelo.evirtual.domain.Customer;
import com.erebelo.evirtual.domain.enums.CustomerType;
import com.erebelo.evirtual.domain.enums.Profile;
import com.erebelo.evirtual.dto.CustomerDTO;
import com.erebelo.evirtual.dto.CustomerNewDTO;
import com.erebelo.evirtual.repositories.AddressRepository;
import com.erebelo.evirtual.repositories.CustomerRepository;
import com.erebelo.evirtual.security.UserSpringSecurity;
import com.erebelo.evirtual.services.exceptions.AuthorizationException;
import com.erebelo.evirtual.services.exceptions.DataIntegrityException;
import com.erebelo.evirtual.services.exceptions.ObjectNotFoundException;
import com.erebelo.evirtual.services.security.UserService;

@Service
public class CustomerService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private CustomerRepository repo;

	@Autowired
	private AddressRepository addressRepository;

	public Customer find(Integer id) {

		UserSpringSecurity user = UserService.authenticated();

		// Checking if the user has permission to access
		if (user == null || !user.hasRole(Profile.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Access denied");
		}

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
			throw new DataIntegrityException("You cannot remove it because there are related orders");
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
		return new Customer(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null, null);
	}

	public Customer fromDTO(CustomerNewDTO objDto) {
		Customer c = new Customer(null, objDto.getName(), objDto.getEmail(), objDto.getSsnOrNrle(),
				CustomerType.toEnum(objDto.getType()), bCryptPasswordEncoder.encode(objDto.getPassword()));
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
