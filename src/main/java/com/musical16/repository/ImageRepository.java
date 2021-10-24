package com.musical16.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musical16.Entity.ImageEntity;
import com.musical16.Entity.ProductEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Long>{

	List<ImageEntity> findByProducts(ProductEntity products);
}
