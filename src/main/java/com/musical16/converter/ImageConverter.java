package com.musical16.converter;

import org.springframework.stereotype.Component;

import com.musical16.Entity.ImageEntity;
import com.musical16.dto.ImageDTO;

@Component
public class ImageConverter {

	public ImageDTO toDTO(ImageEntity imageEntity) {
		ImageDTO image = new ImageDTO();
		image.setId(imageEntity.getId());
		image.setName(imageEntity.getName());
		image.setUrl(imageEntity.getUrl());
		image.setMessage("Thành công");
		image.setProduct(imageEntity.getProducts().getName());
		image.setCreatedBy(imageEntity.getCreatedBy());
		image.setCreatedDate(imageEntity.getCreatedDate());
		image.setModifiedBy(imageEntity.getModifiedBy());
		image.setModifiedDate(imageEntity.getModifiedDate());
		return image;
	}
}
