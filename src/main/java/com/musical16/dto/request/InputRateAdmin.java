package com.musical16.dto.request;

import javax.validation.constraints.NotNull;

public class InputRateAdmin {

	@NotNull(message = "Mã đánh giá không được rỗng")
	private Long id;
	
	@NotNull(message = "Trạng thái đánh giá không được rỗng")
	private Integer status;
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
