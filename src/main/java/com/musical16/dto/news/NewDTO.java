package com.musical16.dto.news;

import com.musical16.dto.BaseDTO;

public class NewDTO extends BaseDTO{

	private String name;
	private String title;
	private String code;
	private String shortdescription;
	private String detail;
	private String url;
	private Integer status;
	private Long categoryNews;
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
	
	public Long getCategoryNews() {
		return categoryNews;
	}
	public void setCategoryNews(Long categoryNews) {
		this.categoryNews = categoryNews;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
