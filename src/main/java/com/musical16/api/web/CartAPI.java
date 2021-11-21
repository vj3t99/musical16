package com.musical16.api.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.musical16.dto.MessageDTO;
import com.musical16.dto.cart.CartDTO;
import com.musical16.dto.cart.CartDetailDTO;
import com.musical16.service.ICartService;
@PreAuthorize("hasRole('USER')")
@RestController
public class CartAPI {

	@Autowired
	private ICartService cartService;
	
	@GetMapping("/cart")
	public CartDTO showCart(HttpServletRequest req) {
		return cartService.findAll(req);
	}
	
	@PostMapping("/cart")
	public MessageDTO save(@Valid @RequestBody CartDetailDTO cartDetailDTO, HttpServletRequest req) {
		return cartService.save(cartDetailDTO,req);
	}
	
	@DeleteMapping("/cart/{id}")
	public MessageDTO delete(@PathVariable("id") Long id, HttpServletRequest req) {
		return cartService.delete(id,req);
	}
}
