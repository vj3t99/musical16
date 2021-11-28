package com.musical16.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.musical16.Entity.CategoryEntity;
import com.musical16.Entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{

	List<ProductEntity> findAllByOrderByIdDesc();
	
	ProductEntity findByName(String name);
	
	ProductEntity findByCode(String code);
	
	@Query("SELECT p FROM ProductEntity p WHERE CONCAT(p.name, p.price, p.categories.name) LIKE %?1%")
	public List<ProductEntity> search(String keyword);
	
	Page<ProductEntity> findByCategories(CategoryEntity category, Pageable pageable);
	
	List<ProductEntity> findByCategories(CategoryEntity category);
}
