package com.musical16.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.musical16.dto.CategoryDTO;
import com.musical16.dto.MessageDTO;
import com.musical16.service.ICategoryService;

@RestController
public class CategoryAPI {
	
	@Autowired
	private ICategoryService categoryService;

	@GetMapping(value = "/category")
	public List<CategoryDTO> findAll() {
		return categoryService.findAll();	
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/category")
	public MessageDTO save(@RequestBody CategoryDTO categoryDTO, HttpServletRequest req) {
		return categoryService.save(categoryDTO,req);
		
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/category/{id}")
	public MessageDTO delete(@PathVariable("id") long id) {
		 return categoryService.delete(id);
		
	}
}
