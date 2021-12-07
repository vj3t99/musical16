package com.musical16.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.musical16.Entity.CommentEntity;
import com.musical16.Entity.ProductEntity;
import com.musical16.Entity.UserEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long>{

	List<CommentEntity> findByProduct(ProductEntity product, Pageable pageable);
	List<CommentEntity> findByProduct(ProductEntity product);
	CommentEntity findByIdAndUser(Long id, UserEntity user);
}
