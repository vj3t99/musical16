package com.musical16.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.musical16.dto.product.CategoryDTO;

public interface ICategoryService {

	public List<CategoryDTO> findAll();

	public ResponseEntity<?> save(CategoryDTO categoryDTO, HttpServletRequest req);

	public ResponseEntity<?> delete(long id);
}
