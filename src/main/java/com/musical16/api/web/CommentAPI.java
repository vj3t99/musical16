package com.musical16.api.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musical16.dto.comment.CommentDTO;
import com.musical16.dto.request.InputComment;
import com.musical16.dto.request.InputCommentReply;
import com.musical16.dto.response.Page;
import com.musical16.service.ICommentService;

@RestController
public class CommentAPI {

	@Autowired
	private ICommentService commentService;
	
	@GetMapping("/comment/{id}")
	public Page<CommentDTO> showCommentProduct(@PathVariable(value = "id") Long id, @RequestParam(value = "page", required = false) Integer page){
		return commentService.findAll(id, page);
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/comment")
	public ResponseEntity<?> saveComment(HttpServletRequest req,@Valid @RequestBody InputComment comment){
		return commentService.save(comment,req);
	}
	
	@PreAuthorize("hasAnyRole('USER','ADMIN','MANAGER')")
	@DeleteMapping("/comment/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id, HttpServletRequest req){
		return commentService.delete(id,req);
	}
	
	@PreAuthorize("hasAnyRole('USER','ADMIN','MANAGER')")
	@PostMapping("/commentReply")
	public ResponseEntity<?> saveCommentReply(HttpServletRequest req,@Valid @RequestBody InputCommentReply comment){
		return commentService.saveReply(comment,req);
	}
	
	@PreAuthorize("hasAnyRole('USER','ADMIN','MANAGER')")
	@DeleteMapping("/commentReply/{id}")
	public ResponseEntity<?> deleteReply(@PathVariable("id") Long id, HttpServletRequest req){
		return commentService.deleteReply(id,req);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping("/admin/comment")
	public List<CommentDTO> showAll(){
		return commentService.showAllComment();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@DeleteMapping("/admin/comment/{id}")
	public ResponseEntity<?> adminDelete(@PathVariable("id") Long id){
		return commentService.delete(id);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@DeleteMapping("/admin/commentReply/{id}")
	public ResponseEntity<?> adminDeleteReply(@PathVariable("id") Long id){
		return commentService.deleteReply(id);
	}
	
}
