package com.erebelo.evirtual.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.erebelo.evirtual.domain.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	private Integer paymentStatus;

	@JsonIgnore // Solving the cyclically problem
	@OneToOne
	@JoinColumn(name = "customer_order_id")
	@MapsId
	private CustomerOrder customerOrder;

	public Payment() {
	}

	public Payment(Integer id, PaymentStatus paymentStatus, CustomerOrder customerOrder) {
		super();
		this.id = id;
		this.paymentStatus = paymentStatus.getCode();
		this.customerOrder = customerOrder;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PaymentStatus getPaymentStatus() {
		return PaymentStatus.toEnum(paymentStatus);
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus.getCode();
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
