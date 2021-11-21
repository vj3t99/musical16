package com.musical16.dto.cart;

import java.util.List;

public class CartDTO {

	private Long id;
	private List<CartDetailDTO> cartDetail;
	private Integer totalQuantity;
	private Double totalPrice;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<CartDetailDTO> getCartDetail() {
		return cartDetail;
	}
	public void setCartDetail(List<CartDetailDTO> cartDetail) {
		this.cartDetail = cartDetail;
	}
	public Integer getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
