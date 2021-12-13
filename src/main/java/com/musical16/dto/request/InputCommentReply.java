package com.musical16.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class InputCommentReply {

	private Long id;
	@NotNull(message = "Mã bình luận không được rỗng")
	private Long commentId;
	
	@NotEmpty(message = "bình luân không được rỗng")
	@Size(min = 3, max = 100, message = "bình luận phải từ 3 đến 100 kí tự")
	private String message;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
