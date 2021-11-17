package com.musical16.dto;

import org.hibernate.validator.constraints.Email;

public class ForgotPasswordDTO {

	@Email(message="Email không hợp lệ")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
