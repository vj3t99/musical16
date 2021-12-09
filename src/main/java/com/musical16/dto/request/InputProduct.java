package com.musical16.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class InputProduct {

	private Long id;
    
	@NotEmpty(message = "Tên sản phẩm không được rỗng !")
	private String name;
	
	@NotEmpty(message = "Mô tả ngắn không được rỗng !")
	private String shortdescription;
	
	@NotEmpty(message = "Chi tiết sản phẩm không được rỗng !")
	private String detail;
	
	@NotNull(message = "Giá không được rỗng")
	@Min(value = 0, message = "Giá không hợp lệ")
	private Double price;

	@NotNull(message = "Số lượng không được rỗng")
	@Min(value = 0, message = "Mã xuất xứ không hợp lệ")
	private Long originId;
	
	@NotNull(message = "Số lượng không được rỗng")
	@Min(value = 0, message = "Số lượng không hợp lệ")
	private Integer quantity;
	
	private String[] url;
	
	@NotEmpty(message = "Code sản phẩm không được rỗng !")
	private String code;
	
	@NotNull(message = "Mã thể loại không được rỗng")
	@Min(value = 0, message = "Mã thể loại không hợp lệ")
	private Long categoryId;
	
	@NotNull(message = "Bảo hành không được rỗng")
	@Min(value = 0, message = "Bảo hành không hợp lệ")
	private Integer warranty;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortdescription() {
		return shortdescription;
	}

	public void setShortdescription(String shortdescription) {
		this.shortdescription = shortdescription;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getOriginId() {
		return originId;
	}

	public void setOriginId(Long originId) {
		this.originId = originId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String[] getUrl() {
		return url;
	}

	public void setUrl(String[] url) {
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getWarranty() {
		return warranty;
	}

	public void setWarranty(Integer warranty) {
		this.warranty = warranty;
	}
	
	
}
