package com.musical16.api.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.musical16.dto.news.CategoryNewDTO;
import com.musical16.service.ICategoryNewService;

@RestController
public class CategoryNewAPI {
	
	@Autowired
	private ICategoryNewService categoryNewService;

	@GetMapping("/categoryNew")
	public List<CategoryNewDTO> findAll() {
		return categoryNewService.findAll();
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/categoryNew")
	public ResponseEntity<?> insert(@RequestBody CategoryNewDTO categoryNew, HttpServletRequest req) {
		return categoryNewService.save(categoryNew, req);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/categoryNew/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return categoryNewService.delete(id);
	}
}
