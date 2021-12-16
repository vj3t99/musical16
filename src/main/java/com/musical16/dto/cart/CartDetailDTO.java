package com.musical16.dto.cart;



import com.musical16.dto.BaseDTO;
import com.musical16.dto.product.ProductDTO;

public class CartDetailDTO extends BaseDTO{

	private ProductDTO product;
	
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
	
	
	
}
