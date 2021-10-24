package com.musical16.converter;

import org.springframework.stereotype.Component;

import com.musical16.Entity.CategoryEntity;
import com.musical16.dto.CategoryDTO;

@Component
public class CategoryConverter {

	public CategoryEntity toEntity(CategoryDTO categoryDTO) {
		CategoryEntity category = new CategoryEntity();
		category.setName(categoryDTO.getName());
		category.setCode(categoryDTO.getCode());
		return category;		
	}
	
	public CategoryEntity toEntity(CategoryDTO categoryDTO, CategoryEntity category) {
		category.setName(categoryDTO.getName());
		category.setCode(categoryDTO.getCode());
		return category;
	}
	
	public CategoryDTO toDTO(CategoryEntity categoryEntity) {
		CategoryDTO category = new CategoryDTO();
		category.setId(categoryEntity.getId());
		category.setName(categoryEntity.getName());
		category.setCode(categoryEntity.getCode());
		category.setCreatedBy(categoryEntity.getCreatedBy());
		category.setCreatedDate(categoryEntity.getCreatedDate());
		category.setModifiedBy(categoryEntity.getModifiedBy());
		category.setModifiedDate(categoryEntity.getModifiedDate());
		return category;
		
	}
	
	
}
