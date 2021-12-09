package com.musical16.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.musical16.Entity.RateReplyEntity;
import com.musical16.dto.rate.RateReplyDTO;

@Component
public class RateReplyConverter {

	@Autowired
	private UserConverter userConverter;
	
	public RateReplyDTO toDTO(RateReplyEntity item) {
		RateReplyDTO rate = new RateReplyDTO();
		rate.setId(item.getId());
		rate.setUser(userConverter.toDTO(item.getUser()));
		rate.setMessage(item.getMessage());
		rate.setStatus(item.getStatus());
		rate.setCreatedBy(item.getCreatedBy());
		rate.setCreatedDate(item.getCreatedDate());
		rate.setModifiedDate(item.getModifiedDate());
		rate.setModifiedBy(item.getModifiedBy());
		return rate;
	}

	
}
