package com.musical16.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;

import com.musical16.dto.order.OrderDTO;
import com.musical16.dto.request.InputOrderAdmin;
import com.musical16.dto.request.InputOrderDTO;
import com.musical16.dto.response.MessageDTO;

public interface IOrdersService {

	List<OrderDTO> findAll(HttpServletRequest req);

	MessageDTO createOrder(HttpServletRequest req);

	MessageDTO createOrder(HttpServletRequest req, InputOrderDTO order);

	MessageDTO cancelOrder(Long id, HttpServletRequest req);
	
	Long totalItem();

	List<OrderDTO> findAll(Pageable pageable);

	MessageDTO updateOrder(HttpServletRequest req, InputOrderAdmin order);


}
