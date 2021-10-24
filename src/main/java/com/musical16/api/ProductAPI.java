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

import com.musical16.dto.MessageDTO;
import com.musical16.dto.ProductDTO;
import com.musical16.service.IProductService;

@RestController
public class ProductAPI {
	
	@Autowired
	private IProductService productService;

	@GetMapping("/product")
	public List<ProductDTO> findAll() {
		return productService.findAll();
	}
	@GetMapping("/product/{id}")
	public ProductDTO findOne(@PathVariable("id")long id) {
		return productService.findOne(id);
	}

	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/product")
	public MessageDTO save(@RequestBody ProductDTO productDTO, HttpServletRequest req) {
		return productService.save(productDTO, req);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/product/{id}")
	public MessageDTO delete(@PathVariable("id") Long id) {
		return productService.delete(id);
	}
}
