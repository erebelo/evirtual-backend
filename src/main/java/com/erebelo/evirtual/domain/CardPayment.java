package com.erebelo.evirtual.domain;

import javax.persistence.Entity;

import com.erebelo.evirtual.domain.enums.PaymentStatus;

@Entity
public class CardPayment extends Payment {
	private static final long serialVersionUID = 1L;

	private Integer numberOfInstallments;

	public CardPayment() {
	}

	public CardPayment(Integer id, PaymentStatus status, CustomerOrder customerOrder, Integer numberOfInstallments) {
		super(id, status, customerOrder);
		this.numberOfInstallments = numberOfInstallments;
	}

	public Integer getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public void setNumberOfInstallments(Integer numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}
}
