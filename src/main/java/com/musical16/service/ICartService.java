package com.musical16.service;

import javax.servlet.http.HttpServletRequest;

import com.musical16.dto.cart.CartDTO;
import com.musical16.dto.cart.CartDetailDTO;
import com.musical16.dto.response.MessageDTO;

public interface ICartService {

	CartDTO findAll(HttpServletRequest req);

	MessageDTO save(CartDetailDTO cartDetailDTO, HttpServletRequest req);

	MessageDTO delete(Long id, HttpServletRequest req);

	
}
