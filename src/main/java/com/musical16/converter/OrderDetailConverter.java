package com.musical16.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.musical16.Entity.CartDetailEntity;
import com.musical16.Entity.OrderDetailEntity;
import com.musical16.Entity.OrdersEntity;
import com.musical16.dto.order.OrderDetailDTO;
import com.musical16.repository.OrderDetailRepository;

@Component
public class OrderDetailConverter {
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private ProductConverter productConverter;

	public OrderDetailDTO toDTO(OrderDetailEntity eaches) {
		OrderDetailDTO order = new OrderDetailDTO();
		order.setId(eaches.getId());
		order.setOrderId(eaches.getOrder().getId());
		order.setProduct(productConverter.toDTO(eaches.getProduct()));
		order.setQuantity(eaches.getQuantity());
		order.setPrice(eaches.getPrice());
		return order;
	}

	public OrderDetailEntity toOrder(CartDetailEntity each, OrdersEntity order2) {
		OrderDetailEntity order = new OrderDetailEntity();
		order.setOrder(order2);
		order.setPrice(each.getPrice());
		order.setQuantity(each.getQuantity());
		order.setProduct(each.getProduct());
		return orderDetailRepository.save(order);
	}

}
