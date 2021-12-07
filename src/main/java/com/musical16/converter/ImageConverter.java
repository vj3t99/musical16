package com.musical16.converter;

import org.springframework.stereotype.Component;

import com.musical16.Entity.ImageEntity;
import com.musical16.dto.product.ImageDTO;

@Component
public class ImageConverter {

	public ImageDTO toDTO(ImageEntity imageEntity) {
		ImageDTO image = new ImageDTO();
		image.setId(imageEntity.getId());
		image.setUrl(imageEntity.getUrl());
		return image;
	}
}
