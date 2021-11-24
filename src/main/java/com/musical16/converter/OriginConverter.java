package com.musical16.converter;

import org.springframework.stereotype.Component;

import com.musical16.Entity.OriginEntity;
import com.musical16.dto.product.OriginDTO;

@Component
public class OriginConverter {

	public OriginEntity toEntity(OriginDTO originDTO) {
		OriginEntity origin = new OriginEntity();
		origin.setName(originDTO.getName());
		origin.setCode(originDTO.getCode());
		return origin;		
	}
	
	public OriginEntity toEntity(OriginDTO originDTO, OriginEntity origin) {
		origin.setName(originDTO.getName());
		origin.setCode(originDTO.getCode());
		return origin;
	}
	
	public OriginDTO toDTO(OriginEntity categoryEntity) {
		OriginDTO origin = new OriginDTO();
		origin.setId(categoryEntity.getId());
		origin.setName(categoryEntity.getName());
		origin.setCode(categoryEntity.getCode());
		origin.setCreatedBy(categoryEntity.getCreatedBy());
		origin.setCreatedDate(categoryEntity.getCreatedDate());
		origin.setModifiedBy(categoryEntity.getModifiedBy());
		origin.setModifiedDate(categoryEntity.getModifiedDate());
		return origin;
		
	}
}
