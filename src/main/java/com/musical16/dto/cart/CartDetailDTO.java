package com.musical16.dto.cart;


import javax.validation.constraints.Min;
import com.musical16.dto.BaseDTO;
import com.musical16.dto.ProductDTO;

public class CartDetailDTO extends BaseDTO{

	private ProductDTO product;
	private Long productId;
	@Min(value = 1, message = "Số lượng không hợp lệ")
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
