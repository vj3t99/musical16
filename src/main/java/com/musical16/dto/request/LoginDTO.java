package com.musical16.dto.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class LoginDTO {

	@NotBlank
	@NotEmpty(message = "Username không được rỗng !")
	@Size(min = 5, max = 32, message = "Username phải trong khoảng 5 đến 32 kí tự")
    private String username;
	@NotBlank
	@NotEmpty(message = "Password không được rỗng !")
	@Size(min = 5, message = "Pasword phải 5 kí tự trở lên")
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
