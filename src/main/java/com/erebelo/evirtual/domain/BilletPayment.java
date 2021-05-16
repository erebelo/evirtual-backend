package com.erebelo.evirtual.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.erebelo.evirtual.domain.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class BilletPayment extends Payment {
	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dueDate;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date paymentDate;

	public BilletPayment() {
	}

	public BilletPayment(Integer id, PaymentStatus status, CustomerOrder customerOrder, Date dueDate, Date paymentDate) {
		super(id, status, customerOrder);
		this.dueDate = dueDate;
		this.paymentDate = paymentDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
}
