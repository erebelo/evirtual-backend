package com.erebelo.evirtual.domain;

import com.erebelo.evirtual.domain.enums.PaymentStatus;

public class CardPayment extends Payment {
	private static final long serialVersionUID = 1L;

	private Integer numberOfInstallments;

	public CardPayment() {
	}

	public CardPayment(Integer id, PaymentStatus status, Order order, Integer numberOfInstallments) {
		super(id, status, order);
		this.numberOfInstallments = numberOfInstallments;
	}

	public Integer getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public void setNumberOfInstallments(Integer numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}
}
