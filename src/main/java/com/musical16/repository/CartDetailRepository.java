package com.musical16.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musical16.Entity.CartDetailEntity;
import com.musical16.Entity.CartEntity;
import com.musical16.Entity.ProductEntity;

public interface CartDetailRepository extends JpaRepository<CartDetailEntity, Long>{

	List<CartDetailEntity> findByCart(CartEntity cart);
	List<CartDetailEntity> findByCartAndProduct(CartEntity cart, ProductEntity product);
	CartDetailEntity findByIdAndCart(Long id, CartEntity cart);
}
