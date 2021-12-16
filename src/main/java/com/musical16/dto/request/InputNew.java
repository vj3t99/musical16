package com.musical16.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class InputNew {

	private Long id;
	@NotEmpty(message = "Tên bài viết không được rỗng !")
	@Size(min = 5, max = 255, message = "Vui lòng nhập tên trong khoảng 5 đến 255 kí tự")
	private String name;
	
	@NotEmpty(message = "Code bài viết không được rỗng !")
	@Size(min = 3, max = 255, message = "Vui lòng nhập code trong khoảng 3 đến 255 kí tự")
	private String code;
	
	@NotEmpty(message = "Mô tả ngắn không được rỗng !")
	@Size(min = 10, max = 255, message = "Vui lòng nhập mô tả ngắn trong khoảng 10 đến 255 kí tự")
	private String shortdescription;
	
	@NotEmpty(message = "Chi tiết không được rỗng !")
	@Size(min = 20, message = "Chi tiết phải lớn hơn 20 kí tự")
	private String detail;
	
	private String url;
	
	@NotNull(message = "Mã thể loại bài viết không được rỗng !")
	private Long categoryNews;
	
	@NotNull(message = "Bạn chưa nhập status cho bài viết")
	private Integer status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Long getCategoryNews() {
		return categoryNews;
	}
	public void setCategoryNews(Long categoryNews) {
		this.categoryNews = categoryNews;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
