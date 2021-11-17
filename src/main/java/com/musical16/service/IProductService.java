package com.musical16.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.musical16.dto.MessageDTO;
import com.musical16.dto.ProductDTO;

public interface IProductService {
	
	public List<ProductDTO> findAll();

	public MessageDTO save(ProductDTO productDTO, HttpServletRequest req);
	
	public MessageDTO delete(Long id);

	public ProductDTO findOne(long id);
}
