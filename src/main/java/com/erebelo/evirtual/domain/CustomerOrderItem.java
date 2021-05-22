package com.erebelo.evirtual.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CustomerOrderItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore // Solving the cyclically problem
	@EmbeddedId
	private CustomerOrderItemPK id = new CustomerOrderItemPK();

	private Double discount;
	private Integer quantity;
	private Double price;

	public CustomerOrderItem() {
	}

	public CustomerOrderItem(CustomerOrder customerOrder, Product product, Double discount, Integer quantity, Double price) {
		super();
		id.setCustomerOrder(customerOrder);
		id.setProduct(product);
		this.discount = discount;
		this.quantity = quantity;
		this.price = price;
	}

	public double getSubTotal() {
		return (price - discount) * quantity;
	}

	@JsonIgnore // Solving the cyclically problem
	public CustomerOrder getCustomerOrder() {
		return id.getCustomerOrder();
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		id.setCustomerOrder(customerOrder);
	}

	public Product getProduct() {
		return id.getProduct();
	}

	public void setProduct(Product product) {
		id.setProduct(product);
	}

	public CustomerOrderItemPK getId() {
		return id;
	}

	public void setId(CustomerOrderItemPK id) {
		this.id = id;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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
		CustomerOrderItem other = (CustomerOrderItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
