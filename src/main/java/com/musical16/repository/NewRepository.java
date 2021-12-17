package com.musical16.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.musical16.Entity.CategoryNewEntity;
import com.musical16.Entity.NewEntity;

public interface NewRepository extends JpaRepository<NewEntity, Long>{

	NewEntity findByCode(String code);
	List<NewEntity> findByCategoryNewsAndStatus(CategoryNewEntity category, Integer status, Pageable pageable );
	List<NewEntity> findByCategoryNewsAndStatus(CategoryNewEntity category, Integer status);
	List<NewEntity> findByStatus(Integer status, Pageable pageable);
	List<NewEntity> findByStatus(Integer status);
}
