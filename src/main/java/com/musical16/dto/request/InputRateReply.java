package com.musical16.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class InputRateReply{

	private Long id;
	
	@NotNull(message = "Mã đánh giá không được rỗng")
	private Long rateId;
	
	@NotEmpty(message = "Phản hồi đánh giá không được rỗng")
	@Size(min = 3, max = 100, message = "Phản hồi đánh giá phải từ 3 đến 100 kí tự")
	private String message;
	
	private Integer status;
	public Long getRateId() {
		return rateId;
	}
	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
