package com.erebelo.evirtual.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.erebelo.evirtual.services.validation.CustomerInsert;

@CustomerInsert
public class CustomerNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Mandatory fill")
	@Length(min = 5, max = 120, message = "The length must be between 5 and 120 characters")
	private String name;

	@NotEmpty(message = "Mandatory fill")
	private String email;

	@NotEmpty(message = "Mandatory fill")
	private String ssnOrNrle;

	private Integer type;

	@NotEmpty(message = "Mandatory fill")
	private String password;

	// Address
	@NotEmpty(message = "Mandatory fill")
	private String streetAddress;

	@NotEmpty(message = "Mandatory fill")
	private String number;

	private String complement;

	private String district;

	@NotEmpty(message = "Mandatory fill")
	private String zipCode;

	// Phones
	@NotEmpty(message = "Mandatory fill")
	private String phone1;

	private String phone2;

	private String phone3;

	// City
	private Integer cityId;

	public CustomerNewDTO() {
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

	public String getSsnOrNrle() {
		return ssnOrNrle;
	}

	public void setSsnOrNrle(String ssnOrNrle) {
		this.ssnOrNrle = ssnOrNrle;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
}
