package com.musical16.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.musical16.Entity.NewEntity;
import com.musical16.dto.news.NewDTO;
import com.musical16.dto.request.InputNew;

@Component
public class NewConverter {

	@Autowired
	private CategoryNewConverter categoryNewConverter;
	
	public NewEntity toEntity(InputNew input) {
		NewEntity news = new NewEntity();
		news.setName(input.getName());
		news.setCode(input.getCode());
		news.setShortdescription(input.getShortdescription());
		news.setDetail(input.getDetail());
		news.setUrl(input.getUrl());
		return news;
	}

	public NewEntity toEntity(InputNew input, NewEntity news) {
		news.setName(input.getName());
		news.setCode(input.getCode());
		news.setShortdescription(input.getShortdescription());
		news.setDetail(input.getDetail());
		news.setUrl(input.getUrl());
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
		news.setUrl(newEntity.getUrl());
		news.setCategoryNews(categoryNewConverter.toDTO(newEntity.getCategoryNews()).getId());
		news.setCreatedBy(newEntity.getCreatedBy());
		news.setCreatedDate(newEntity.getCreatedDate());
		news.setModifiedBy(newEntity.getModifiedBy());
		news.setModifiedDate(newEntity.getModifiedDate());
		return news;
	}


	
}
