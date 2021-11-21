package com.musical16.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.musical16.Entity.CartDetailEntity;
import com.musical16.dto.cart.CartDetailDTO;
import com.musical16.repository.ProductRepository;

@Component
public class CartDetailConverter {
	
	@Autowired
	private ProductConverter productConverter;
	

	public CartDetailDTO toDTO(CartDetailEntity each) {
		CartDetailDTO cart = new CartDetailDTO();
		cart.setId(each.getId());		
		cart.setProduct(productConverter.toDTO(each.getProduct()));
		cart.setQuantity(each.getQuantity());
		cart.setPrice(each.getPrice());
		cart.setCreatedBy(each.getCreatedBy());
		cart.setCreatedDate(each.getCreatedDate());
		cart.setModifiedBy(each.getModifiedBy());
		cart.setModifiedDate(each.getModifiedDate());
		return cart;
	}


	
}
