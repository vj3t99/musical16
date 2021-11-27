package com.musical16.service;


import javax.servlet.http.HttpServletRequest;

import com.musical16.dto.product.ProductDTO;
import com.musical16.dto.response.MessageDTO;
import com.musical16.dto.response.Page;

public interface IProductService {
	
	public Page<ProductDTO> findAll(Integer page, String[] sort);

	public MessageDTO save(ProductDTO productDTO, HttpServletRequest req);
	
	public MessageDTO delete(Long id);

	public ProductDTO findOne(long id);

	public Page<ProductDTO> search(String key);
}
