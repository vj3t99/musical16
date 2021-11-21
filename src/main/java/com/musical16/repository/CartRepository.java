package com.musical16.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musical16.Entity.CartEntity;
import com.musical16.Entity.UserEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long>{
	CartEntity findByUser(UserEntity user);

}
