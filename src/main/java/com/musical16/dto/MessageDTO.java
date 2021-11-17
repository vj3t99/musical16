package com.musical16.dto;

public class MessageDTO {

	private String Message;

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public MessageDTO(String message) {
		super();
		Message = message;
	}
	public MessageDTO() {
		
	}
}
