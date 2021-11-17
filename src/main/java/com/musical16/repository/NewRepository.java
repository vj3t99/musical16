package com.musical16.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musical16.Entity.NewEntity;

public interface NewRepository extends JpaRepository<NewEntity, Long>{

	NewEntity findByCode(String code);
}
