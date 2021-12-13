package com.musical16.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class InputRate {

	@NotEmpty(message = "Mã đánh giá không được rỗng")
	private Long id;
	
	@NotEmpty(message = "Đánh giá không được rỗng")
	@Size(min = 3, max = 100, message = "Đánh giá phải từ 3 đến 100 kí tự")
	private String message;
	
	@NotNull(message = "Điểm đánh giá không được rỗng")
	@Min(value = 1, message = "Điểm đánh giá phải lớn hơn 1")
	@Max(value = 5, message = "Điểm đánh giá phải bé hơn 5")
	private Integer point;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	
}
