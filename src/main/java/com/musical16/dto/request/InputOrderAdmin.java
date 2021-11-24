package com.musical16.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class InputOrderAdmin {

	@NotNull(message = "Trường id không được rỗng")
	private Long id;
	
	@NotNull(message = "Trường trạng thái không được rỗng")
	@Min(value = -1)
	@Max(value = 2)
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
