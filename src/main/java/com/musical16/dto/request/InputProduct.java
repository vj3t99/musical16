package com.musical16.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class InputProduct {

	private Long id;
    
	@NotEmpty(message = "Tên sản phẩm không được rỗng !")
	@Size(min = 3, max = 255, message = "Vui lòng nhập tên trong khoảng 3 đến 255 kí tự")
	private String name;
	
	@NotEmpty(message = "Mô tả ngắn không được rỗng !")
	@Size(min = 10, max = 255, message = "Vui lòng nhập mô tả ngắn trong khoảng 10 đến 255 kí tự")
	private String shortdescription;
	
	@NotEmpty(message = "Chi tiết sản phẩm không được rỗng !")
	@Size(min = 20, message = "Vui lòng nhập chi tiết trên 20 kí tự")
	private String detail;
	
	@NotNull(message = "Giá không được rỗng")
	@Min(value = 0, message = "Giá không hợp lệ")
	private Double price;

	@NotNull(message = "Số lượng không được rỗng")
	@Min(value = 0, message = "Mã xuất xứ không hợp lệ")
	private Long originId;
	
	@NotNull(message = "Số lượng không được rỗng")
	@Min(value = 0, message = "Số lượng phải là số dương")
	@Max(value = 1000, message = "Số lượng không thể lớn hơn 1000")
	private Integer quantity;
	
	private String[] url;
	
	@NotEmpty(message = "Code sản phẩm không được rỗng !")
	@Size(min = 3, max = 255, message = "Vui lòng nhập code trong khoảng 3 đến 255 kí tự")
	private String code;
	
	@NotNull(message = "Mã thể loại không được rỗng")
	@Min(value = 0, message = "Mã thể loại không hợp lệ")
	private Long categoryId;
	
	@NotNull(message = "Bảo hành không được rỗng")
	@Min(value = 0, message = "Bảo hành phải là số dương")
	@Max(value = 100, message = "Bảo hành phải nhỏ hơn 100")
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
