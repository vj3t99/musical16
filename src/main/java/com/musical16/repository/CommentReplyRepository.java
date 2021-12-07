package com.musical16.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musical16.Entity.CommentReplyEntity;
import com.musical16.Entity.UserEntity;

public interface CommentReplyRepository extends JpaRepository<CommentReplyEntity, Long>{

	CommentReplyEntity findByIdAndUser(Long id, UserEntity user);
}
