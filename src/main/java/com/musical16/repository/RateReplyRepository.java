package com.musical16.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musical16.Entity.RateReplyEntity;
import com.musical16.Entity.UserEntity;

public interface RateReplyRepository extends JpaRepository<RateReplyEntity, Long>{

	RateReplyEntity findByIdAndUser(Long id, UserEntity user);

}
