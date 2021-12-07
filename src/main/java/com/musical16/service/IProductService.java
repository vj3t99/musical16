package com.musical16.service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.musical16.dto.product.ProductDTO;
import com.musical16.dto.request.InputProduct;
import com.musical16.dto.response.Page;

public interface IProductService {
	
	public Page<ProductDTO> findAll(Long id, Integer page, String[] sort);

	public ResponseEntity<?> save(InputProduct input, HttpServletRequest req);
	
	public ResponseEntity<?> delete(Long id);

	public ResponseEntity<?> findOne(long id);

	public Page<ProductDTO> search(String key, Integer page);

	public ResponseEntity<?> showAll();
}
