package com.musical16.dto.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class InputOrderDTO {

	@NotBlank
	@Size(min = 9, max = 11, message = "số điện thoại phải trong khoảng từ 9 đến 11 số")
	private String phone;

	@NotEmpty(message = "Trường địa chỉ không được rỗng")
	@Size(min = 10, max = 32, message = "Trường địa chỉ phải nằm trong khoảng 10 đến 32 kí tự")
	private String address;

	@NotEmpty(message = "Trường họ trên không được rỗng")
	@Size(min = 6, max = 32, message = "Trường họ tên phải nằm trong khoảng từ 6 đến 32 kí tự")
	private String name;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
