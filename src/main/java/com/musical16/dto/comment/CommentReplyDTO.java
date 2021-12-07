package com.musical16.dto.comment;

import com.musical16.dto.BaseDTO;
import com.musical16.dto.response.UserDTO;

public class CommentReplyDTO extends BaseDTO{

	private Long commentId;
	private UserDTO user;
	private String message;
	private Integer status;
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
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
