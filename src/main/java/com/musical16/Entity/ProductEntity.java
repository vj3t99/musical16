package com.musical16.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class ProductEntity extends BaseEntity{

	@Column(name = "name")
	private String name;
	
	@Column(name = "shortdescription")
	private String shortdescription;
	
	@Column(name = "detail")
	private String detail;
	
	@Column(name = "price")
	private Double price;

	@ManyToOne
	@JoinColumn(name = "origin_id")
	private OriginEntity origins;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@OneToMany(mappedBy = "products")
	private List<ImageEntity> images = new ArrayList<>();
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "code", unique = true)
	private String code;
	
	@ManyToOne
	@JoinColumn(name = "categoryp_id")
	private CategoryEntity categories;
	
	@Column(name = "wanrranty")
	private Integer wanrranty;

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

	public OriginEntity getOrigins() {
		return origins;
	}

	public void setOrigins(OriginEntity origins) {
		this.origins = origins;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	public Integer getWanrranty() {
		return wanrranty;
	}

	public void setWanrranty(Integer wanrranty) {
		this.wanrranty = wanrranty;
	}

	public List<ImageEntity> getImages() {
		return images;
	}

	public void setImages(List<ImageEntity> images) {
		this.images = images;
	}

	public CategoryEntity getCategories() {
		return categories;
	}

	public void setCategories(CategoryEntity categories) {
		this.categories = categories;
	}
	
	
}
