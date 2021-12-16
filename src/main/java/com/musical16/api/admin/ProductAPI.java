package com.musical16.api.admin;



import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musical16.dto.product.ProductDTO;
import com.musical16.dto.request.InputProduct;
import com.musical16.dto.response.Page;
import com.musical16.service.IProductService;

@RestController
public class ProductAPI {
	
	@Autowired
	private IProductService productService;
	
	@GetMapping("/search/{key}")
	public Page<ProductDTO> search(@PathVariable(value = "key",required = false) String key, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "sort", required = false) String[] sort, @RequestParam(value = "category", required = false) Long id){
		return productService.search(key, page, sort, id);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping("/admin/product")
	public ResponseEntity<?> showAll(){
		return productService.showAll();
	}
	
	@GetMapping("/product")
	public Page<ProductDTO> findAll(@RequestParam(value = "category", required = false) Long id, @RequestParam(value ="page", required = false) Integer page, @RequestParam(value = "sort", required = false) String[] sort) {
		return productService.findAll(id,page,sort);
	}
	@GetMapping("/product/{id}")
	public ResponseEntity<?> findOne(@PathVariable("id")long id) {
		return productService.findOne(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/product")
	public ResponseEntity<?> save(@Valid @RequestBody InputProduct input, HttpServletRequest req) {
		return productService.save(input, req);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		return productService.delete(id);
	}
}
