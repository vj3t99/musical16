package com.musical16.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginDTO {

	@NotEmpty(message = "Username không được rỗng !")
	@Size(min = 6, max = 32, message = "Username phải trong khoảng 6 đến 32 kí tự")
    private String username;
	@NotEmpty(message = "Password không được rỗng !")
	@Size(min = 6, message = "Pasword phải 6 kí tự trở lên")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
