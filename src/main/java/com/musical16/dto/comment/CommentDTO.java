package com.musical16.dto.comment;

import java.util.ArrayList;
import java.util.List;

import com.musical16.dto.BaseDTO;
import com.musical16.dto.response.UserDTO;

public class CommentDTO extends BaseDTO{

	private Long productId;
	private UserDTO user;
	private List<CommentReplyDTO> commentReply = new ArrayList<>();
	private String message;
	private Integer status;
	
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public List<CommentReplyDTO> getCommentReply() {
		return commentReply;
	}
	public void setCommentReply(List<CommentReplyDTO> commentReply) {
		this.commentReply = commentReply;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
