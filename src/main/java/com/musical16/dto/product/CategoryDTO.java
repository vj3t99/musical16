package com.musical16.dto.product;

import com.musical16.dto.BaseDTO;

public class CategoryDTO extends BaseDTO{
	private String name;
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