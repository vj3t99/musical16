package com.musical16.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.musical16.Entity.ImageEntity;
import com.musical16.Entity.ProductEntity;
import com.musical16.dto.product.ImageDTO;
import com.musical16.dto.product.ProductDTO;
import com.musical16.dto.request.InputProduct;
import com.musical16.repository.CategoryRepository;
import com.musical16.repository.ImageRepository;
import com.musical16.repository.OriginRepository;

@Component
public class ProductConverter {
	
	@Autowired
	private OriginRepository originRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ImageRepository imageRepository;

	public ProductEntity toEntity(InputProduct input) {
		ProductEntity product = new ProductEntity();
		product.setName(input.getName());
		product.setShortdescription(input.getShortdescription());
		product.setDetail(input.getDetail());
		product.setPrice(input.getPrice());
		product.setOrigins(originRepository.findOne(input.getOriginId()));
		product.setQuantity(input.getQuantity());
		product.setCode(input.getCode());
		product.setCategories(categoryRepository.findOne(input.getCategoryId()));
		product.setWanrranty(input.getWanrranty());
		product.setRate_point(0.0);
		if(product.getQuantity()>0) {
			product.setStatus(1);
		}else {
			product.setStatus(0);
		}
		List<ImageEntity> listImage = new ArrayList<>();
		try {
			for(String each : input.getUrl()) {
				ImageEntity item = new ImageEntity();
				item.setUrl(each);
				item.setProducts(product);
				listImage.add(item);
			}
		} catch (NullPointerException e) {
			ImageEntity item = new ImageEntity();
			item.setUrl("");
			item.setProducts(product);
			listImage.add(item);
		}
		product.setImages(listImage);
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
		List<ImageDTO> imageDTO = new ArrayList<>();
		for(ImageEntity each : productEntity.getImages()) {
			ImageDTO image = new ImageDTO();
			image.setId(each.getId());
			image.setUrl(each.getUrl());
			imageDTO.add(image);
			
		}
		product.setImages(imageDTO);
		product.setCode(productEntity.getCode());
		product.setCategoryId(productEntity.getCategories().getId());
		product.setWanrranty(productEntity.getWanrranty());
		product.setRate(productEntity.getRate_point());
		product.setCreatedBy(productEntity.getCreatedBy());
		product.setCreatedDate(productEntity.getCreatedDate());
		product.setModifiedBy(productEntity.getModifiedBy());
		product.setModifiedDate(productEntity.getModifiedDate());
		return product;
	}

	public ProductEntity toEntity(InputProduct input, ProductEntity product) {
		product.setName(input.getName());
		product.setShortdescription(input.getShortdescription());
		product.setDetail(input.getDetail());
		product.setPrice(input.getPrice());
		product.setOrigins(originRepository.findOne(input.getOriginId()));
		product.setQuantity(input.getQuantity());
		product.setCode(input.getCode());
		product.setCategories(categoryRepository.findOne(input.getCategoryId()));
		product.setWanrranty(input.getWanrranty());
		if(product.getQuantity()>0) {
			product.setStatus(1);
		}else {
			product.setStatus(0);
		}
		for(ImageEntity each : imageRepository.findByProducts(product)) {
			imageRepository.delete(each);
		}
		List<ImageEntity> listImage = new ArrayList<>();
		try {
			for(String each : input.getUrl()) {
				ImageEntity item = new ImageEntity();
				item.setUrl(each);
				item.setProducts(product);
				listImage.add(item);
			}
		} catch (NullPointerException e) {
			ImageEntity item = new ImageEntity();
			item.setUrl("");
			item.setProducts(product);
			listImage.add(item);
		}
		product.setImages(listImage);
		return product;
	}
}
