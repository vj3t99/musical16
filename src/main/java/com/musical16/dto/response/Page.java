package com.musical16.dto.response;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {

	private Integer page;
	private Integer totalPage;
	List<T> list = new ArrayList<>();
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
}
