package com.musical16.dto.response;

public class ResponseDTO<T> {

	private String message;
	private T object;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
	
}
