package com.musical16.dto.order;

import java.util.ArrayList;
import java.util.List;

import com.musical16.dto.BaseDTO;

public class OrderDTO extends BaseDTO{

	private Long userId;
	

	private List<OrderDetailDTO> orderDetail = new ArrayList<>();
	

	private Double totalPrice;
	

	private Integer totalQuantity;
	

	private String phone;
	

	private String address;
	

	private String name;
	

	private String email;
	

	private Integer status;
	
	
	
	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public List<OrderDetailDTO> getOrderDetail() {
		return orderDetail;
	}


	public void setOrderDetail(List<OrderDetailDTO> orderDetail) {
		this.orderDetail = orderDetail;
	}


	public Double getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}


	public Integer getTotalQuantity() {
		return totalQuantity;
	}


	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
