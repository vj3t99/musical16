package com.musical16.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class InputComment {

	private Long id;
	
	@NotNull(message = "Mã sản phẩm không được rỗng")
	private Long productId;
	
	@NotEmpty(message = "bình luân không được rỗng")
	@Size(min = 3, max = 100, message = "bình luận phải từ 3 đến 100 kí tự")
	private String message;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	
}
