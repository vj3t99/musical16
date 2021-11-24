package com.musical16.converter;

import org.springframework.stereotype.Component;

import com.musical16.Entity.CategoryNewEntity;
import com.musical16.dto.news.CategoryNewDTO;

@Component
public class CategoryNewConverter {

	public CategoryNewEntity toEntity(CategoryNewDTO categoryNew) {
		CategoryNewEntity category = new CategoryNewEntity();
		category.setName(categoryNew.getName());
		category.setCode(categoryNew.getCode());
		return category;
	}

	public CategoryNewEntity toEntity(CategoryNewDTO categoryNew, CategoryNewEntity category) {
		category.setName(categoryNew.getName());
		category.setCode(categoryNew.getCode());
		return category;
	}

	public CategoryNewDTO toDTO(CategoryNewEntity categoryNew) {
		CategoryNewDTO category = new CategoryNewDTO();
		category.setId(categoryNew.getId());
		category.setName(categoryNew.getName());
		category.setCode(categoryNew.getCode());
		category.setCreatedBy(categoryNew.getCreatedBy());
		category.setCreatedDate(categoryNew.getCreatedDate());
		category.setModifiedBy(categoryNew.getModifiedBy());
		category.setModifiedDate(categoryNew.getModifiedDate());
		return category;
	}

}
