package com.musical16.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.musical16.Entity.RateReplyEntity;
import com.musical16.dto.rate.RateReplyDTO;
import com.musical16.dto.request.InputRateReply;

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

	public RateReplyEntity toEntity(RateReplyEntity oldReply, InputRateReply input) {
		RateReplyEntity rateReply = oldReply;
		rateReply.setMessage(input.getMessage());
		rateReply.setStatus(input.getStatus());
		return rateReply;
	}

	public RateReplyEntity toEntity(InputRateReply input) {
		RateReplyEntity reply = new RateReplyEntity();
		reply.setStatus(input.getStatus());
		reply.setMessage(input.getMessage());
		return reply;
	}

	
}
