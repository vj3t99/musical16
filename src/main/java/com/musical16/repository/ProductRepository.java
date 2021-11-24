package com.musical16.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musical16.Entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{

	List<ProductEntity> findAllByOrderByIdDesc();
	ProductEntity findByName(String name);
	ProductEntity findByCode(String code);
}
