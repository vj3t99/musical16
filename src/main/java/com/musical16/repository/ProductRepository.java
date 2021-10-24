package com.musical16.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musical16.Entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{

	ProductEntity findByName(String name);
	ProductEntity findByCode(String code);
}
