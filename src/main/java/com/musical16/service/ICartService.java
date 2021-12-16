package com.musical16.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.musical16.dto.cart.CartDTO;
import com.musical16.dto.request.InputCart;

public interface ICartService {

	CartDTO findAll(HttpServletRequest req);

	ResponseEntity<?> delete(Long id, HttpServletRequest req);

	ResponseEntity<?> save(InputCart input, HttpServletRequest req);

	
}
