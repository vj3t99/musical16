package com.musical16.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.musical16.Entity.CartDetailEntity;
import com.musical16.Entity.CartEntity;
import com.musical16.Entity.OrderDetailEntity;
import com.musical16.Entity.OrdersEntity;
import com.musical16.Entity.ProductEntity;
import com.musical16.Entity.RateEntity;
import com.musical16.Entity.UserEntity;
import com.musical16.converter.OrderConverter;
import com.musical16.dto.order.OrderDTO;
import com.musical16.dto.request.InputOrderAdmin;
import com.musical16.dto.request.InputOrderDTO;
import com.musical16.dto.response.ResponseDTO;
import com.musical16.repository.CartRepository;
import com.musical16.repository.OrdersRepository;
import com.musical16.repository.ProductRepository;
import com.musical16.repository.RateRepository;
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
	
	@Autowired
	private RateRepository rateRepository;

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
	public ResponseEntity<?> createOrder(HttpServletRequest req) {
		ResponseDTO<OrderDTO> result = new ResponseDTO<>();
		UserEntity user = userRepository.findByUserName(helpService.getName(req));
		CartEntity cart = cartRepository.findByUser(user);
		OrdersEntity orderEntity = new OrdersEntity();
		if (user != null && cart != null) {
			ordersRepository.save(orderEntity);
			boolean flag = true;
			for(CartDetailEntity each : cart.getCartDetail()) {
				if(each.getQuantity()>each.getProduct().getQuantity()) {
					flag = false;
					break;
				}
			}	
			if(flag) {
				orderEntity = orderConverter.toEntity(cart, orderEntity);
				orderEntity.setPhone(user.getPhone());
				orderEntity.setName(user.getFullName());
				orderEntity.setAddress(user.getAddress());
				orderEntity.setCreatedBy(user.getUserName());
				orderEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				ordersRepository.save(orderEntity);
				result.setMessage("Tạo đơn hàng thành công");
				result.setObject(orderConverter.toDTO(orderEntity));
				return ResponseEntity.ok(result);
			}else {
				result.setMessage("Chúng tôi không đủ số lượng để cung cấp, vui lòng kiểm tra lại giỏ hàng");
				return ResponseEntity.badRequest().body(result);
			}
		} else {
			result.setMessage("Đã có lỗi xảy ra !");
			return ResponseEntity.badRequest().body(result);
		}
	}
	

	@Override
	public ResponseEntity<?> createOrder(HttpServletRequest req, InputOrderDTO order) {
		ResponseDTO<OrderDTO> result = new ResponseDTO<>();
		UserEntity user = userRepository.findByUserName(helpService.getName(req));
		CartEntity cart = cartRepository.findByUser(user);
		OrdersEntity orderEntity = new OrdersEntity();
		if (user != null && cart != null) {
			ordersRepository.save(orderEntity);
			boolean flag = true;
			for(CartDetailEntity each : cart.getCartDetail()) {
				if(each.getQuantity()>each.getProduct().getQuantity()) {
					flag = false;
					break;
				}
			}	
			if(flag) {
				orderEntity = orderConverter.toEntity(cart, orderEntity);
				orderEntity.setPhone(order.getPhone());
				orderEntity.setName(order.getName());
				orderEntity.setAddress(order.getAddress());
				orderEntity.setCreatedBy(user.getUserName());
				orderEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				ordersRepository.save(orderEntity);
				result.setMessage("Tạo đơn hàng thành công");
				result.setObject(orderConverter.toDTO(orderEntity));
				return ResponseEntity.ok(result);
			}else {
				result.setMessage("Chúng tôi không đủ số lượng để cung cấp, vui lòng kiểm tra lại giỏ hàng");
				return ResponseEntity.badRequest().body(result);
			}
		} else {
			result.setMessage("Đã có lỗi xảy ra !");
			return ResponseEntity.badRequest().body(result);
		}
	}

	@Override
	public ResponseEntity<?> cancelOrder(Long id, HttpServletRequest req) {
		ResponseDTO<OrderDTO> result = new ResponseDTO<>();
		UserEntity user = userRepository.findByUserName(helpService.getName(req));
		OrdersEntity list = ordersRepository.findByIdAndUser(id,user);
		if(list!=null) {
			if(list.getId().equals(id)&&list.getStatus().equals(0)) {
				list.setStatus(-1);
				list.setModifiedBy(user.getUserName());
				list.setModifiedDate(new Timestamp(System.currentTimeMillis()));
				ordersRepository.save(list);
				result.setMessage("Xóa đơn hàng thành công");
				result.setObject(orderConverter.toDTO(list));
				return ResponseEntity.ok(result);
			}else {
				result.setMessage("Đơn hàng không thể thao tác");
				return ResponseEntity.badRequest().body(result);
			}
		}else {
			result.setMessage("Đơn hàng không tồn tại hoặc bạn không thể thao tác");
			return ResponseEntity.badRequest().body(result);
		}
	}

	@Override
	public Long totalItem() {	
		return ordersRepository.count();
	}

	@Override
	public List<OrderDTO> findAll() {
		List<OrderDTO> list = new ArrayList<>();
		for(OrdersEntity each : ordersRepository.findAll()) {
			list.add(orderConverter.toDTO(each));
		}
		return list;
	}

	@Override
	public ResponseEntity<?> updateOrder(HttpServletRequest req, InputOrderAdmin order) {
		ResponseDTO<OrderDTO> result = new ResponseDTO<>();
		OrdersEntity orderEntity = ordersRepository.findOne(order.getId());
		if(orderEntity!=null) {
			if(orderEntity.getStatus().equals(0)) {
				if(order.getStatus().equals(-1)) {
					orderEntity.setStatus(0);
					ordersRepository.save(orderEntity);
					result.setMessage("Xóa đơn hàng thành công");
					result.setObject(orderConverter.toDTO(orderEntity));
					return ResponseEntity.ok(result);
				}else if(order.getStatus().equals(1)) {
					if(updateProduct(orderEntity)) {
						orderEntity.setStatus(1);
						ordersRepository.save(orderEntity);
						result.setMessage("Đơn hàng "+orderEntity.getId()+ " đã được duyệt");
						result.setObject(orderConverter.toDTO(orderEntity));
						return ResponseEntity.ok(result);
					}else {
						result.setMessage("Hàng trong kho không đủ số lượng cung cấp");
						return ResponseEntity.badRequest().body(result);
					}
					
				}else {
					result.setMessage("Thao tác không hợp lệ");
					return ResponseEntity.badRequest().body(result);
				}
			}else if(orderEntity.getStatus().equals(1)) {
				if(order.getStatus().equals(2)) {
					orderEntity.setStatus(2);
					ordersRepository.save(orderEntity);
					result.setMessage("Đơn hàng đã xác nhận thanh toán");
					for(OrderDetailEntity each : orderEntity.getOrderDetail()) {
						RateEntity rate = new RateEntity();
						rate.setProduct(each.getProduct());
						rate.setUser(orderEntity.getUser());
						rate.setFlag(false);
						rate.setStatus(0);
						rate.setMessage("");
						rateRepository.save(rate);
					}
					result.setObject(orderConverter.toDTO(orderEntity));
					return ResponseEntity.ok(result);
				}else if(order.getStatus().equals(-1)){
					returnProduct(orderEntity);
					orderEntity.setStatus(-1);
					ordersRepository.save(orderEntity);
					result.setMessage("Đơn hàng đã hoàn trả thành công");
					result.setObject(orderConverter.toDTO(orderEntity));
					return ResponseEntity.ok(result);
				}else {
					result.setMessage("Thao tác không hợp lệ");
					return ResponseEntity.badRequest().body(result);
				}
			}else {
				result.setMessage("Đơn hàng này đã chốt bạn không thể thao tác");
				return ResponseEntity.badRequest().body(result);
			}
		}else {
			result.setMessage("Không tìm thấy mã đơn hàng hoặc mã đơn hàng không hợp lệ");
			return ResponseEntity.badRequest().body(result);
		}
	}

	private void returnProduct(OrdersEntity orderEntity) {
		for(OrderDetailEntity each : orderEntity.getOrderDetail()) {
			ProductEntity product = each.getProduct();
			product.setQuantity(product.getQuantity()+each.getQuantity());
			if(product.getQuantity()==0) {
				product.setStatus(0);
			}
			productRepository.save(product);
		}
		
	}

	private boolean updateProduct(OrdersEntity orderEntity) {
		Boolean flag = true;
		for(OrderDetailEntity each : orderEntity.getOrderDetail()) {
			if(each.getQuantity()<=each.getProduct().getQuantity()) {
				ProductEntity product = each.getProduct();
				product.setQuantity(product.getQuantity()-each.getQuantity());
				if(product.getQuantity()==0) {
					product.setStatus(0);
				}
				productRepository.save(product);
			}else {
				flag = false;
			}
		}
		return flag;
	}
}
