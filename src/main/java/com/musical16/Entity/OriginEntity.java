package com.musical16.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "origin")
public class OriginEntity extends BaseEntity{
	@Column(name = "name", unique = true)
	private String name;
	@Column(name = "code")
	private String code;
	
	@OneToMany(mappedBy = "origins", cascade = CascadeType.ALL)
	private List<ProductEntity> products = new ArrayList<>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<ProductEntity> getProducts() {
		return products;
	}
	public void setProducts(List<ProductEntity> products) {
		this.products = products;
	}
	
}
