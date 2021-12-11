package com.musical16.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.musical16.dto.news.CategoryNewDTO;

public interface ICategoryNewService {

	ResponseEntity<?> save(CategoryNewDTO categoryNew, HttpServletRequest req);

	List<CategoryNewDTO> findAll();

	ResponseEntity<?> delete(Long id);

}
