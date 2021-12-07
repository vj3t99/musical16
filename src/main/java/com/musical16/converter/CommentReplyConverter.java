package com.musical16.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.musical16.Entity.CommentReplyEntity;
import com.musical16.dto.comment.CommentReplyDTO;

@Component
public class CommentReplyConverter {

	@Autowired
	private UserConverter userConverter;
	
	public CommentReplyDTO toDTO(CommentReplyEntity item) {
		CommentReplyDTO c = new CommentReplyDTO();
		c.setId(item.getId());
		c.setCreatedBy(item.getCreatedBy());
		c.setCreatedDate(item.getCreatedDate());
		c.setModifiedBy(item.getModifiedBy());
		c.setModifiedDate(item.getModifiedDate());
		c.setCommentId(item.getComment().getId());
		c.setUser(userConverter.toDTO(item.getUser()));
		c.setMessage(item.getMessage());
		c.setStatus(item.getStatus());
		return c;
	}

}
