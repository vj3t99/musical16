package com.musical16.dto.cart;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.musical16.dto.BaseDTO;
import com.musical16.dto.product.ProductDTO;

public class CartDetailDTO extends BaseDTO{

	private ProductDTO product;
	
	@NotNull(message = "Mã sản phẩm không được rỗng")
	private Long productId;
	
	@NotNull(message = "Số lượng không được rỗng")
	@Min(value = 1, message = "Số lượng phải lớn hơn 1")
	@Max(value = 1000, message = "Số lượng phải nhỏ hơn 1000")
	private Integer quantity; 
	
	private Double price;

	
	public ProductDTO getProduct() {
		return product;
	}
	public void setProduct(ProductDTO product) {
		this.product = product;
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
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	
	
}
