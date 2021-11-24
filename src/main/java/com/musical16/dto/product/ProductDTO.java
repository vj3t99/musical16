package com.musical16.dto.product;

import java.util.ArrayList;
import java.util.List;

import com.musical16.dto.BaseDTO;


public class ProductDTO extends BaseDTO{

	private String name;
	
	private String shortdescription;
	
	private String detail;
	
	private Double price;

	private Long originId;
	
	private Integer quantity;
	
	private List<ImageDTO> images = new ArrayList<>();
	
	private Integer status;
	
	private String code;
	
	private Long categoryId;
	
	private Integer wanrranty;
	
	private String message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortdescription() {
		return shortdescription;
	}

	public void setShortdescription(String shortdescription) {
		this.shortdescription = shortdescription;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}


	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public List<ImageDTO> getImages() {
		return images;
	}

	public void setImages(List<ImageDTO> images) {
		this.images = images;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getOriginId() {
		return originId;
	}

	public void setOriginId(Long originId) {
		this.originId = originId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getWanrranty() {
		return wanrranty;
	}

	public void setWanrranty(Integer wanrranty) {
		this.wanrranty = wanrranty;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
