package com.musical16.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class InputCart {

	private Long id;
	private Long productId;
	@NotNull(message = "Số lượng không được rỗng")
	@Min(value = 1, message = "Số lượng phải lớn hơn 1")
	@Max(value = 1000, message = "Số lượng phải nhỏ hơn 1000")
	private Integer quantity;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
