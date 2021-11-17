package com.musical16.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "image")
public class ImageEntity extends BaseEntity{

	@Column(name = "name")
	private String name;
	@Column(name = "url")
	private String url;
	
	@ManyToOne
	@JoinColumn(name = "products_id")
	private ProductEntity products;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ProductEntity getProducts() {
		return products;
	}
	public void setProducts(ProductEntity products) {
		this.products = products;
	}
	
}
