package com.musical16.dto.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class ChangePassword {

	@NotBlank
	@NotEmpty(message = "Password không được rỗng !")
	@Size(min = 5, max = 20, message = "Pasword phải từ 5 đến 20 kí tự !")
	private String password;
	
	@NotBlank
	@NotEmpty(message = "Password không được rỗng !")
	@Size(min = 5, max = 20, message = "Pasword phải từ 5 đến 20 kí tự !")
	private String newPassword;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
