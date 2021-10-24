package com.musical16.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musical16.Entity.OriginEntity;

public interface OriginRepository extends JpaRepository<OriginEntity, Long>{

	OriginEntity findByCode(String code);
}
