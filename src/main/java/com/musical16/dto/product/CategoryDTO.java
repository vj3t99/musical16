package com.musical16.dto.product;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.musical16.dto.BaseDTO;

public class CategoryDTO extends BaseDTO{
	
	@NotEmpty(message = "Tên thể loại không được rỗng !")
	@Size(min = 5, max = 32, message = "Tên thể loại phải trong khoảng 5 đến 32 kí tự")
	private String name;
	
	@NotEmpty(message = "Code thể loại không được rỗng !")
	@Size(min = 3, max = 32, message = "Code thể loại phải trong khoảng 3 đến 32 kí tự")
	private String code;
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
	
}
