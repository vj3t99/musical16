package com.musical16.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.musical16.dto.order.OrderDTO;
import com.musical16.dto.request.InputOrderAdmin;
import com.musical16.dto.request.InputOrderDTO;

public interface IOrdersService {

	List<OrderDTO> findAll(HttpServletRequest req);

	ResponseEntity<?> createOrder(HttpServletRequest req);

	ResponseEntity<?> createOrder(HttpServletRequest req, InputOrderDTO order);

	ResponseEntity<?> cancelOrder(Long id, HttpServletRequest req);
	
	Long totalItem();

	List<OrderDTO> findAll();

	ResponseEntity<?> updateOrder(HttpServletRequest req, InputOrderAdmin order);

	ResponseEntity<?> findAll(HttpServletRequest req, Long id);

	ResponseEntity<?> findOne(Long id);


}
