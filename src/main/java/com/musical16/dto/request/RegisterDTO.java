package com.musical16.dto.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class RegisterDTO {

	@NotBlank
	@NotEmpty(message = "Username không được rỗng !")
	@Size(min = 5, max = 32, message = "Username phải trong khoảng 5 đến 32 kí tự !")
	private String username;

	@NotBlank
	@NotEmpty(message = "Password không được rỗng !")
	@Size(min = 5, max = 20, message = "Pasword phải từ 5 đến 20 kí tự !")
	private String password;

	@NotEmpty(message = "fullname không được rỗng !")
	@Size(min = 8, max = 32, message = "Họ tên phải từ 8 đến 32 kí tự !")
	private String fullname;

	@NotEmpty(message = "Bạn chưa nhập email  !")
	@Email(message = "định dạng Email sai!")
	private String email;
	
	@NotEmpty(message = "Địa chỉ không được rỗng !")
	@Size(min = 8, max = 32, message = "Địa chỉ phải từ 8 đến 32 kí tự !")
	private String address;
	
	private Integer sex;
	
	@NotBlank
	@NotEmpty(message = "Bạn chưa nhập số điện thoại !")
	@Size(min = 9, max = 11, message = "Số điện thoại không hợp lệ")
	private String phone;


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
}
