package com.musical16.dto.rate;

import com.musical16.dto.BaseDTO;
import com.musical16.dto.response.UserDTO;

public class RateReplyDTO extends BaseDTO{
	private Long rateId;
	private UserDTO user;
	private String message;
	private Integer status;
	public Long getRateId() {
		return rateId;
	}
	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}
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
