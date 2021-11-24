package com.musical16.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_detail")
public class OrderDetailEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private OrdersEntity order;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private ProductEntity productOrder;
	
	@Column(name= "quantity")
	private Integer quantity;
	
	@Column(name = "price")
	private Double price;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductEntity getProduct() {
		return productOrder;
	}

	public void setProduct(ProductEntity product) {
		this.productOrder = product;
	}

	public OrdersEntity getOrder() {
		return order;
	}

	public void setOrder(OrdersEntity order) {
		this.order = order;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	
}