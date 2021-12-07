package com.musical16.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.musical16.Entity.CommentEntity;
import com.musical16.Entity.CommentReplyEntity;
import com.musical16.dto.comment.CommentDTO;
import com.musical16.dto.comment.CommentReplyDTO;

@Component
public class CommentConverter {

	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private CommentReplyConverter commentReplyConverter;
	
	public CommentDTO toDTO(CommentEntity each) {
		CommentDTO comment = new CommentDTO();
		comment.setId(each.getId());
		comment.setCreatedBy(each.getCreatedBy());
		comment.setCreatedDate(each.getCreatedDate());
		comment.setModifiedBy(each.getModifiedBy());
		comment.setModifiedDate(each.getModifiedDate());
		comment.setProductId(each.getProduct().getId());
		comment.setUser(userConverter.toDTO(each.getUser()));
		comment.setMessage(each.getMessage());
		comment.setStatus(each.getStatus());
		List<CommentReplyDTO> list = new ArrayList<>();
		for(CommentReplyEntity item : each.getCommentReply()) {
			list.add(commentReplyConverter.toDTO(item));
		}
		comment.setCommentReply(list);
		return comment;
	}

}
