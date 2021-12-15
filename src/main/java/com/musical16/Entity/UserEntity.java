package com.musical16.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {

	@Column(name = "username", unique = true)
	private String userName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "fullname")
	private String fullName;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "sex")
	private Integer sex;
	
	@Column(name = "email", unique = true)
	private String email;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "token")
	private String token;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_role",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<RoleEntity> roles = new ArrayList<>();
	
	@OneToOne(mappedBy = "user")
    private CartEntity cart;
	
	@OneToMany(mappedBy = "user")
	private List<OrdersEntity> order = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private List<CommentEntity> comment = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private List<CommentReplyEntity> commentReply = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private List<RateEntity> rate = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private List<RateReplyEntity> rateReply = new ArrayList<>();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleEntity> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public CartEntity getCart() {
		return cart;
	}

	public void setCart(CartEntity cart) {
		this.cart = cart;
	}

	public List<OrdersEntity> getOrder() {
		return order;
	}

	public void setOrder(List<OrdersEntity> order) {
		this.order = order;
	}

	public List<CommentEntity> getComment() {
		return comment;
	}

	public void setComment(List<CommentEntity> comment) {
		this.comment = comment;
	}

	public List<CommentReplyEntity> getCommentReply() {
		return commentReply;
	}

	public void setCommentReply(List<CommentReplyEntity> commentReply) {
		this.commentReply = commentReply;
	}

	public List<RateEntity> getRate() {
		return rate;
	}

	public void setRate(List<RateEntity> rate) {
		this.rate = rate;
	}

	public List<RateReplyEntity> getRateReply() {
		return rateReply;
	}

	public void setRateReply(List<RateReplyEntity> rateReply) {
		this.rateReply = rateReply;
	}

	
	
	
	
	
	
}