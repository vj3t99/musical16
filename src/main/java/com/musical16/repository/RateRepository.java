package com.musical16.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.musical16.Entity.ProductEntity;
import com.musical16.Entity.RateEntity;
import com.musical16.Entity.UserEntity;

public interface RateRepository extends JpaRepository<RateEntity, Long>{

	List<RateEntity> findByUserAndFlag(UserEntity user, Boolean flag);
	RateEntity findByIdAndUser(Long id, UserEntity user);
	List<RateEntity> findByProductAndFlag(ProductEntity product, Boolean flag);
	List<RateEntity> findByProductAndFlag(ProductEntity product, Boolean flag, Pageable pageable);
}
