package com.musical16.dto;

public class TokenDTO {
	private String token;
	private String message;

	public TokenDTO(String token,String message) {
		super();
		this.token = token;
		this.message = message;
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
