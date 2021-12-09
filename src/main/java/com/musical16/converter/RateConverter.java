package com.musical16.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.musical16.Entity.RateEntity;
import com.musical16.Entity.RateReplyEntity;
import com.musical16.dto.rate.RateDTO;
import com.musical16.dto.rate.RateReplyDTO;
import com.musical16.dto.request.InputRate;

@Component
public class RateConverter {
	
	@Autowired
	private ProductConverter productConverter;
	
	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private RateReplyConverter rateReplyConverter;

	public RateDTO toDTO(RateEntity each) {
		RateDTO rate = new RateDTO();
		rate.setId(each.getId());
		rate.setProduct(productConverter.toDTO(each.getProduct()));
		rate.setUser(userConverter.toDTO(each.getUser()));
		rate.setMessage(each.getMessage());
		rate.setStatus(each.getStatus());
		rate.setFlag(each.getFlag());
		rate.setPoint(each.getPoint());
		List<RateReplyDTO> list = new ArrayList<>();
		for(RateReplyEntity item : each.getRateReply()) {
			RateReplyDTO rateDTO = rateReplyConverter.toDTO(item);
			list.add(rateDTO);
		}
		rate.setRateReply(list);
		rate.setCreatedBy(each.getCreatedBy());
		rate.setCreatedDate(each.getCreatedDate());
		rate.setModifiedBy(each.getModifiedBy());
		rate.setModifiedDate(each.getModifiedDate());
		return rate;
	}

	public RateEntity toEntity(InputRate input, RateEntity old) {
		RateEntity rate = old;
		rate.setMessage(input.getMessage());
		rate.setPoint(input.getPoint());
		rate.setFlag(true);
		return rate;
	}

}
