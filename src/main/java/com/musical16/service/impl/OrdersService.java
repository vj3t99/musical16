package com.musical16.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.musical16.Entity.CartEntity;
import com.musical16.Entity.OrderDetailEntity;
import com.musical16.Entity.OrdersEntity;
import com.musical16.Entity.ProductEntity;
import com.musical16.Entity.UserEntity;
import com.musical16.converter.OrderConverter;
import com.musical16.dto.order.OrderDTO;
import com.musical16.dto.request.InputOrderAdmin;
import com.musical16.dto.request.InputOrderDTO;
import com.musical16.dto.response.MessageDTO;
import com.musical16.repository.CartRepository;
import com.musical16.repository.OrdersRepository;
import com.musical16.repository.ProductRepository;
import com.musical16.repository.UserRepository;
import com.musical16.service.IHelpService;
import com.musical16.service.IOrdersService;

@Service
public class OrdersService implements IOrdersService {

	@Autowired
	private OrdersRepository ordersRepository;

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IHelpService helpService;

	@Autowired
	private OrderConverter orderConverter;

	@Override
	public List<OrderDTO> findAll(HttpServletRequest req) {
		List<OrdersEntity> listOrder = ordersRepository.findByUser(userRepository.findByUserName(helpService.getName(req)));
		List<OrderDTO> orderDTO = new ArrayList<>();
		for (OrdersEntity each : listOrder) {
			orderDTO.add(orderConverter.toDTO(each));
		}
		return orderDTO;
	}

	@Override
	public MessageDTO createOrder(HttpServletRequest req) {
		MessageDTO message = new MessageDTO();
		UserEntity user = userRepository.findByUserName(helpService.getName(req));
		CartEntity cart = cartRepository.findByUser(user);
		OrdersEntity orderEntity = new OrdersEntity();
		if (user != null && cart != null) {
			ordersRepository.save(orderEntity);
			orderEntity = orderConverter.toEntity(cart, orderEntity);
			orderEntity.setPhone(user.getPhone());
			orderEntity.setName(user.getFullName());
			orderEntity.setAddress(user.getAddress());
			orderEntity.setCreatedBy(user.getUserName());
			orderEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			ordersRepository.save(orderEntity);
			message.setMessage("Tạo đơn hàng thành công");
		} else {
			message.setMessage("Tài khoản không tồn tại");
		}
		return message;
	}

	@Override
	public MessageDTO createOrder(HttpServletRequest req, InputOrderDTO order) {
		MessageDTO message = new MessageDTO();
		UserEntity user = userRepository.findByUserName(helpService.getName(req));
		CartEntity cart = cartRepository.findByUser(user);
		OrdersEntity orderEntity = new OrdersEntity();
		if (user != null && cart != null) {
			ordersRepository.save(orderEntity);
			orderEntity = orderConverter.toEntity(cart, orderEntity);
			orderEntity.setPhone(order.getPhone());
			orderEntity.setName(order.getName());
			orderEntity.setAddress(order.getAddress());
			orderEntity.setCreatedBy(user.getUserName());
			orderEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			ordersRepository.save(orderEntity);
			message.setMessage("Tạo đơn hàng thành công");
		} else {
			message.setMessage("Tài khoản không tồn tại");
		}
		return message;
	}

	@Override
	public MessageDTO cancelOrder(Long id, HttpServletRequest req) {
		MessageDTO message = new MessageDTO();
		UserEntity user = userRepository.findByUserName(helpService.getName(req));
		List<OrdersEntity> list = ordersRepository.findByUser(user);
		if(!list.isEmpty()) {
			for(OrdersEntity each : list) {
				if(each.getId().equals(id)&&each.getStatus().equals(0)) {
					each.setStatus(-1);
					each.setModifiedBy(user.getUserName());
					each.setModifiedDate(new Timestamp(System.currentTimeMillis()));
					ordersRepository.save(each);
					message.setMessage("Hủy đơn hàng thành công");
				}else {
					message.setMessage("Bạn không thể hủy đơn hàng này");
				}
			}
		}else {
			message.setMessage("Bạn chưa có đơn hàng nào");
		}
		return message;
	}

	@Override
	public Long totalItem() {	
		return ordersRepository.count();
	}

	@Override
	public List<OrderDTO> findAll(Pageable pageable) {
		List<OrderDTO> list = new ArrayList<>();
		for(OrdersEntity each : ordersRepository.findAllByOrderByIdDesc(pageable)) {
			list.add(orderConverter.toDTO(each));
		}
		return list;
	}

	@Override
	public MessageDTO updateOrder(HttpServletRequest req, InputOrderAdmin order) {
		MessageDTO message = new MessageDTO();
		OrdersEntity orderEntity = ordersRepository.findOne(order.getId());
		if(orderEntity!=null) {
			if(orderEntity.getStatus().equals(0)) {
				if(order.getStatus().equals(-1)) {
					orderEntity.setStatus(0);
					ordersRepository.save(orderEntity);
					message.setMessage("Đã hủy đơn hàng "+orderEntity.getId()+" thành công");
				}else if(order.getStatus().equals(1)) {
					if(updateProduct(orderEntity)) {
						orderEntity.setStatus(1);
						ordersRepository.save(orderEntity);
						message.setMessage("Đơn hàng "+orderEntity.getId()+ " đã được duyệt");
					}else {
						message.setMessage("Hàng trong kho không đủ số lượng cung cấp");
					}
					
				}else {
					message.setMessage("Thao tác không hợp lệ");
				}
			}else if(orderEntity.getStatus().equals(1)) {
				if(order.getStatus().equals(2)) {
					orderEntity.setStatus(2);
					ordersRepository.save(orderEntity);
					message.setMessage("Đơn hàng đã xác nhận thanh toán");
				}else if(order.getStatus().equals(-1)){
					returnProduct(orderEntity);
					orderEntity.setStatus(-1);
					ordersRepository.save(orderEntity);
					message.setMessage("Đơn hàng đã hoàn trả thành công");
				}else {
					message.setMessage("Thao tác không hợp lệ");
				}
			}else {
				message.setMessage("Đơn hàng này đã chốt bạn không thể thao tác");
			}
		}else {
			message.setMessage("Không tìm thấy mã đơn hàng hoặc mã đơn hàng không hợp lệ");
		}
		return message;
	}

	private void returnProduct(OrdersEntity orderEntity) {
		for(OrderDetailEntity each : orderEntity.getOrderDetail()) {
			ProductEntity product = each.getProduct();
			product.setQuantity(product.getQuantity()+each.getQuantity());
			productRepository.save(product);
		}
		
	}

	private boolean updateProduct(OrdersEntity orderEntity) {
		Boolean flag = true;
		for(OrderDetailEntity each : orderEntity.getOrderDetail()) {
			if(each.getQuantity()<=each.getProduct().getQuantity()) {
				ProductEntity product = each.getProduct();
				product.setQuantity(product.getQuantity()-each.getQuantity());
				productRepository.save(product);
			}else {
				flag = false;
			}
		}
		return flag;
	}


}
