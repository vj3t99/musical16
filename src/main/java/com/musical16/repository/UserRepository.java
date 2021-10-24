package com.musical16.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musical16.Entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

	UserEntity findByUserName(String name);
	UserEntity findByToken(String token);
	UserEntity findByEmail(String email);
}
