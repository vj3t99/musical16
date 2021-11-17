package com.musical16.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "news")
public class NewEntity extends BaseEntity{

	@Column(name = "name")
	private String name;
	@Column(name = "title")
	private String title;
	@Column(name = "code")
	private String code;
	@Column(name = "shortdescription")
	private String shortdescription;
	@Column(name = "detail")
	private String detail;
	@Column(name = "image")
	private String image;
	@Column(name = "url")
	private String url;
	@Column(name = "status")
	private Integer status;
	@ManyToOne
	@JoinColumn(name = "categoryNew_id")
	private CategoryNewEntity categoryNews;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public CategoryNewEntity getCategoryNews() {
		return categoryNews;
	}
	public void setCategoryNews(CategoryNewEntity categoryNews) {
		this.categoryNews = categoryNews;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
