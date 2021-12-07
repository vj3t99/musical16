package com.musical16.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.musical16.dto.comment.CommentDTO;
import com.musical16.dto.request.InputComment;
import com.musical16.dto.request.InputCommentReply;
import com.musical16.dto.response.Page;

public interface ICommentService {

	Page<CommentDTO> findAll(Long id, Integer page);

	ResponseEntity<?> save(InputComment comment, HttpServletRequest req);

	ResponseEntity<?> delete(Long id, HttpServletRequest req);

	ResponseEntity<?> saveReply(InputCommentReply comment, HttpServletRequest req);

	ResponseEntity<?> deleteReply(Long id, HttpServletRequest req);

}
