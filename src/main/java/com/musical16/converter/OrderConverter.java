package com.musical16.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.musical16.Entity.CartDetailEntity;
import com.musical16.Entity.CartEntity;
import com.musical16.Entity.OrderDetailEntity;
import com.musical16.Entity.OrdersEntity;
import com.musical16.dto.order.OrderDTO;
import com.musical16.dto.order.OrderDetailDTO;

@Component
public class OrderConverter {
	
	@Autowired
	private OrderDetailConverter orderDetailConverter;

	public OrderDTO toDTO(OrdersEntity each) {
		OrderDTO dto = new OrderDTO();
		dto.setId(each.getId());
		dto.setUserId(each.getUser().getId());
		List<OrderDetailDTO> orderDetailDTO = new ArrayList<>();
		for(OrderDetailEntity eaches : each.getOrderDetail()) {
			orderDetailDTO.add(orderDetailConverter.toDTO(eaches));
		}
		dto.setOrderDetail(orderDetailDTO);
		dto.setPhone(each.getPhone());
		dto.setAddress(each.getAddress());
		dto.setEmail(each.getEmail());
		dto.setName(each.getName());
		dto.setStatus(each.getStatus());
		dto.setTotalQuantity(each.getTotalQuantity());
		dto.setTotalPrice(each.getTotalPrice());
		dto.setCreatedBy(each.getCreatedBy());
		dto.setCreatedDate(each.getCreatedDate());
		dto.setModifiedBy(each.getModifiedBy());
		dto.setModifiedDate(each.getModifiedDate());
		return dto;
	}

	public OrdersEntity toEntity(CartEntity cart, OrdersEntity order) {
		order.setUser(cart.getUser());
		order.setTotalPrice(cart.getTotalPrice());
		order.setTotalQuantity(cart.getTotalQuantity());
		order.setEmail(cart.getUser().getEmail());
		List<OrderDetailEntity> orderDetail = new ArrayList<>();
		for(CartDetailEntity each : cart.getCartDetail()) {
			orderDetail.add(orderDetailConverter.toOrder(each,order));
		}		
		order.setOrderDetail(orderDetail);
		order.setStatus(0);
		return order;
	}

}
