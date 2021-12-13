package com.musical16.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category_new")
public class CategoryNewEntity extends BaseEntity{

	@Column(name = "name", unique = true)
	private String name;
	@Column(name = "code")
	private String code;
	@OneToMany(mappedBy = "categoryNews", cascade = CascadeType.ALL)
	private List<NewEntity> news = new ArrayList<>();
	
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
	public List<NewEntity> getNews() {
		return news;
	}
	public void setNews(List<NewEntity> news) {
		this.news = news;
	}
	
	
}
