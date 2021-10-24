package com.musical16.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.musical16.dto.CategoryNewDTO;
import com.musical16.dto.MessageDTO;

public interface ICategoryNewService {

	MessageDTO save(CategoryNewDTO categoryNew, HttpServletRequest req);

	List<CategoryNewDTO> findAll();

	MessageDTO delete(Long id);

}
