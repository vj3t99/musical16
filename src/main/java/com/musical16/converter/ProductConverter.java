package com.musical16.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.musical16.Entity.ImageEntity;
import com.musical16.Entity.ProductEntity;
import com.musical16.dto.product.ImageDTO;
import com.musical16.dto.product.ProductDTO;
import com.musical16.repository.CategoryRepository;
import com.musical16.repository.OriginRepository;

@Component
public class ProductConverter {
	
	@Autowired
	private OriginRepository originRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public ProductEntity toEntity(ProductDTO productDTO) {
		ProductEntity product = new ProductEntity();
		product.setName(productDTO.getName());
		product.setShortdescription(productDTO.getShortdescription());
		product.setDetail(productDTO.getDetail());
		product.setPrice(productDTO.getPrice());
		product.setOrigins(originRepository.findOne(productDTO.getOriginId()));
		product.setQuantity(productDTO.getQuantity());
		product.setCode(productDTO.getCode());
		product.setCategories(categoryRepository.findOne(productDTO.getCategoryId()));
		product.setWanrranty(productDTO.getWanrranty());
		return product;
	}

	public ProductDTO toDTO(ProductEntity productEntity) {
		ProductDTO product = new ProductDTO();
		product.setId(productEntity.getId());
		product.setName(productEntity.getName());
		product.setShortdescription(productEntity.getShortdescription());
		product.setDetail(productEntity.getDetail());
		product.setPrice(productEntity.getPrice());
		product.setOriginId(productEntity.getOrigins().getId());
		product.setQuantity(productEntity.getQuantity());
		product.setStatus(productEntity.getStatus());
		List<ImageEntity> imageEntity = productEntity.getImages();
		List<ImageDTO> imageDTO = new ArrayList<>();
		for(ImageEntity each : imageEntity) {
			ImageDTO image = new ImageDTO();
			image.setId(each.getId());
			image.setName(each.getName());
			image.setUrl(each.getUrl());
			image.setCreatedBy(each.getCreatedBy());
			image.setCreatedDate(each.getCreatedDate());
			image.setModifiedBy(each.getModifiedBy());
			image.setModifiedDate(each.getModifiedDate());
			image.setProduct(productEntity.getName());
			imageDTO.add(image);
			
		}
		product.setImages(imageDTO);
		product.setCode(productEntity.getCode());
		product.setCategoryId(productEntity.getCategories().getId());
		product.setWanrranty(productEntity.getWanrranty());
		product.setCreatedBy(productEntity.getCreatedBy());
		product.setCreatedDate(productEntity.getCreatedDate());
		product.setModifiedBy(productEntity.getModifiedBy());
		product.setModifiedDate(productEntity.getModifiedDate());
		return product;
	}

	public ProductEntity toEntity(ProductDTO productDTO, ProductEntity product) {
		product.setName(productDTO.getName());
		product.setShortdescription(productDTO.getShortdescription());
		product.setDetail(productDTO.getDetail());
		product.setPrice(productDTO.getPrice());
		product.setOrigins(originRepository.findOne(productDTO.getOriginId()));
		product.setQuantity(productDTO.getQuantity());
		product.setCode(productDTO.getCode());
		product.setCategories(categoryRepository.findOne(productDTO.getCategoryId()));
		product.setWanrranty(productDTO.getWanrranty());
		return product;
	}
}
