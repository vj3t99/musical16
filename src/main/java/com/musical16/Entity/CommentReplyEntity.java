package com.musical16.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comment_reply")
public class CommentReplyEntity extends BaseEntity{

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "comment_id")
	private CommentEntity comment;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "status")
	private String status;

	public CommentEntity getComment() {
		return comment;
	}

	public void setComment(CommentEntity comment) {
		this.comment = comment;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
}
