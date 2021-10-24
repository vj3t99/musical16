package com.musical16.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.musical16.dto.CategoryDTO;
import com.musical16.dto.MessageDTO;

public interface ICategoryService {

	public List<CategoryDTO> findAll();

	public MessageDTO save(CategoryDTO categoryDTO, HttpServletRequest req);

	public MessageDTO delete(long id);
}
