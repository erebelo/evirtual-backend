package com.erebelo.evirtual.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.erebelo.evirtual.domain.Customer;

public class CustomerDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Mandatory fill")
	@Length(min = 5, max = 120, message = "The length must be between 5 and 120 characters")
	private String name;

	@NotEmpty(message = "Mandatory fill")
	@Email(message = "Invalid email")
	private String email;

	public CustomerDTO() {
	}

	public CustomerDTO(Customer obj) {
		id = obj.getId();
		name = obj.getName();
		email = obj.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
