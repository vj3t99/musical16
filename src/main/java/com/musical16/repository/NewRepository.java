package com.musical16.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.musical16.Entity.CategoryNewEntity;
import com.musical16.Entity.NewEntity;

public interface NewRepository extends JpaRepository<NewEntity, Long>{

	NewEntity findByCode(String code);
	List<NewEntity> findByCategoryNews(CategoryNewEntity category, Pageable pageable );
}
