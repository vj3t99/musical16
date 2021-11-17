package com.musical16.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musical16.Entity.CategoryNewEntity;

public interface CategoryNewRepository extends JpaRepository<CategoryNewEntity, Long>{

	CategoryNewEntity findByCode(String code);
}
