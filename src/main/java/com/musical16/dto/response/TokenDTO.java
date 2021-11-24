package com.musical16.dto.response;

public class TokenDTO {
	private String token;
	private String message;
	private UserDTO user;
	public TokenDTO(String token,String message, UserDTO user) {
		super();
		this.token = token;
		this.message = message;
		this.user = user;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public TokenDTO() {
		super();
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
