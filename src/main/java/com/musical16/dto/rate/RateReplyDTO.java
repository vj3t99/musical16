package com.musical16.dto.rate;

import com.musical16.dto.BaseDTO;
import com.musical16.dto.response.UserDTO;

public class RateReplyDTO extends BaseDTO{
	private UserDTO user;
	private String message;
	private Integer status;
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
