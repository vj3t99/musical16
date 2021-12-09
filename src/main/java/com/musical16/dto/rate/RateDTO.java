package com.musical16.dto.rate;

import java.util.ArrayList;
import java.util.List;

import com.musical16.dto.BaseDTO;
import com.musical16.dto.product.ProductDTO;
import com.musical16.dto.response.UserDTO;

public class RateDTO extends BaseDTO{

	private ProductDTO product;
	private UserDTO user;
	private List<RateReplyDTO> rateReply = new ArrayList<>();
	private Integer point;
	private String message;
	private Integer status;
	private Boolean flag;
	public ProductDTO getProduct() {
		return product;
	}
	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public List<RateReplyDTO> getRateReply() {
		return rateReply;
	}
	public void setRateReply(List<RateReplyDTO> rateReply) {
		this.rateReply = rateReply;
	}
	
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
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
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	
}
