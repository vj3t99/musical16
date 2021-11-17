package com.musical16.converter;

import org.springframework.stereotype.Component;

import com.musical16.Entity.NewEntity;
import com.musical16.dto.NewDTO;

@Component
public class NewConverter {

	public NewEntity toEntity(NewDTO newDTO) {
		NewEntity news = new NewEntity();
		news.setName(newDTO.getName());
		news.setCode(newDTO.getCode());
		news.setShortdescription(newDTO.getShortdescription());
		news.setDetail(newDTO.getDetail());
		news.setImage(newDTO.getImage());
		news.setUrl(newDTO.getUrl());
		return news;
	}

	public NewEntity toEntity(NewDTO newDTO, NewEntity news) {
		news.setName(newDTO.getName());
		news.setCode(newDTO.getCode());
		news.setShortdescription(newDTO.getShortdescription());
		news.setDetail(newDTO.getDetail());
		news.setImage(newDTO.getImage());
		news.setUrl(newDTO.getUrl());
		return news;
	}

	public NewDTO toDTO(NewEntity newEntity) {
		NewDTO news = new NewDTO();
		news.setId(newEntity.getId());
		news.setName(newEntity.getName());
		news.setTitle(newEntity.getTitle());
		news.setCode(newEntity.getCode());
		news.setShortdescription(newEntity.getShortdescription());
		news.setDetail(newEntity.getDetail());
		news.setImage(newEntity.getImage());
		news.setUrl(newEntity.getUrl());
		news.setCreatedBy(newEntity.getCreatedBy());
		news.setCreatedDate(newEntity.getCreatedDate());
		news.setModifiedBy(newEntity.getModifiedBy());
		news.setModifiedDate(newEntity.getModifiedDate());
		return news;
	}

	
}
