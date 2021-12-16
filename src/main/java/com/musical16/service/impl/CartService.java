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
import com.musical16.Entity.ProductEntity;
import com.musical16.Entity.UserEntity;
import com.musical16.converter.CartDetailConverter;
import com.musical16.dto.cart.CartDTO;
import com.musical16.dto.cart.CartDetailDTO;
import com.musical16.dto.request.InputCart;
import com.musical16.dto.response.ResponseDTO;
import com.musical16.repository.CartDetailRepository;
import com.musical16.repository.CartRepository;
import com.musical16.repository.ProductRepository;
import com.musical16.repository.UserRepository;
import com.musical16.service.ICartService;
import com.musical16.service.IHelpService;

@Service
public class CartService implements ICartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartDetailRepository cartDetailRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private IHelpService helpService;

	@Autowired
	private CartDetailConverter cartDetailConverter;

	@Override
	public CartDTO findAll(HttpServletRequest req) {
		CartDTO cartDTO = new CartDTO();
		try {
			UserEntity user = userRepository.findByUserName(helpService.getName(req));
			CartEntity cart = cartRepository.findByUser(user);
			updateCart(cart);
			List<CartDetailDTO> cartDetailDTO = new ArrayList<>();
			if (cart != null) {
				for (CartDetailEntity each : cartDetailRepository.findByCart(cart)) {
					cartDetailDTO.add(cartDetailConverter.toDTO(each));
				}
			}
			cartDTO.setId(cart.getId());
			cartDTO.setTotalPrice(cart.getTotalPrice());
			cartDTO.setTotalQuantity(cart.getTotalQuantity());
			cartDTO.setCartDetail(cartDetailDTO);
		} catch (NullPointerException e) {

		}
		return cartDTO;
	}

	@Override
	public ResponseEntity<?> save(InputCart input, HttpServletRequest req) {
		ResponseDTO<CartDetailDTO> result = new ResponseDTO<>();
		try {
			UserEntity user = userRepository.findByUserName(helpService.getName(req));
			CartEntity cart = cartRepository.findByUser(user);
			CartDetailEntity cartDetail = new CartDetailEntity();		
			ProductEntity product = productRepository.findOne(input.getProductId());
			
			//Nếu không có id chi tiết giỏ hàng thì thêm mới
			if (input.getId() == null) {
				List<CartDetailEntity> listCartDetail = cartDetailRepository.findByCartAndProduct(cart, product);
				//Nếu sản phẩm đã tồn tại trong giỏ hàng thì cộng thêm số lượng
				if (listCartDetail.size()==1) {
					for(CartDetailEntity each : listCartDetail)
						cartDetail = each;
					cartDetail.setQuantity(input.getQuantity()+cartDetail.getQuantity());
					//Kiểm tra số lượng của giỏ hàng với số lượng của kho hàng đang có
					if (cartDetail.getQuantity()+input.getQuantity() <= product.getQuantity()) {
						cartDetail.setPrice(product.getPrice() * cartDetail.getQuantity());
						cartDetail.setModifiedBy(helpService.getName(req));
						cartDetail.setModifiedDate(new Timestamp(System.currentTimeMillis()));
						cartDetailRepository.save(cartDetail);
						result.setMessage("Thêm số lượng vào giỏ hàng thành công");
						result.setObject(cartDetailConverter.toDTO(cartDetail));
						updateCart(cart);
						return ResponseEntity.ok(result);
						
					} else {
						result.setMessage("Shop chúng tôi không có đủ số lượng để cung cấp");
						return ResponseEntity.badRequest().body(result);
					}
				// Nếu sản phẩm không tồn tại trong giỏ hàng thì tạo 1 sản phẩm mới trong giỏ hàng
				} else {
					cartDetail.setCart(cart);
					cartDetail.setProduct(product);
					cartDetail.setQuantity(input.getQuantity());
					//Kiểm tra số lượng của giỏ hàng với số lượng của kho hàng đang có
					if (input.getQuantity() <= product.getQuantity()) {
						cartDetail.setPrice(product.getPrice() * cartDetail.getQuantity());
						cartDetail.setCreatedBy(helpService.getName(req));
						cartDetail.setCreatedDate(new Timestamp(System.currentTimeMillis()));
						cartDetailRepository.save(cartDetail);
						result.setMessage("Thêm sản phẩm vào giỏ hàng thành công");
						result.setObject(cartDetailConverter.toDTO(cartDetail));
						updateCart(cart);
						return ResponseEntity.ok(result);
						
					} else {
						result.setMessage("Shop chúng tôi không có đủ số lượng để cung cấp");
						return ResponseEntity.badRequest().body(result);
					}
				}
			//Nếu có id thì sẽ cập nhật số lượng sản phẩm
			} else {
				cartDetail = cartDetailRepository.findOne(input.getId());		
				cartDetail.setQuantity(input.getQuantity());
				//Kiểm tra số lượng của giỏ hàng với số lượng của kho hàng đang có
				if (input.getQuantity() <= cartDetail.getProduct().getQuantity()) {
					cartDetail.setPrice(cartDetail.getProduct().getPrice() * cartDetail.getQuantity());
					cartDetail.setModifiedBy(helpService.getName(req));
					cartDetail.setModifiedDate(new Timestamp(System.currentTimeMillis()));
					cartDetailRepository.save(cartDetail);
					result.setMessage("Cập nhật số lượng sản phẩm thành công");
					result.setObject(cartDetailConverter.toDTO(cartDetail));
					updateCart(cart);
					return ResponseEntity.ok(result);
				} else {
					result.setMessage("Shop chúng tôi không có đủ hàng để cung cấp");
					return ResponseEntity.badRequest().body(result);
				}

			}
		} catch (NullPointerException e) {
			result.setMessage("Sản phẩm không tồn tại hoặc giỏ hàng của bạn chưa được tạo");
			return ResponseEntity.badRequest().body(result);
		}
	}
	
	private void updateCart(CartEntity cart) {
		Integer Quantity = 0;
		Double Price = 0.0;
		if(cart!=null) {
			for(CartDetailEntity each : cart.getCartDetail()) {
				Quantity += each.getQuantity();
				Price += each.getPrice();		
			}	
		}
		cart.setTotalQuantity(Quantity);
		cart.setTotalPrice(Price);
		cartRepository.save(cart);
		
	}

	@Override
	public ResponseEntity<?> delete(Long id, HttpServletRequest req) {
		ResponseDTO<CartDetailDTO> result = new ResponseDTO<>();
		CartEntity cart = cartRepository.findByUser(userRepository.findByUserName(helpService.getName(req)));
		if(cart!=null) {
			CartDetailEntity detail = cartDetailRepository.findByIdAndCart(id,cart);
			if(detail!=null) {
				cartDetailRepository.delete(detail);
				result.setMessage("Xóa chi tiết giỏ hàng thành công");
				result.setObject(cartDetailConverter.toDTO(detail));
				return ResponseEntity.ok(result);
			}else {
				result.setMessage("Chi tiết giỏ hàng không tồn tại");
				return ResponseEntity.badRequest().body(result);
			}
		}else {
			result.setMessage("Giỏ hàng không tồn tại");
			return ResponseEntity.badRequest().body(result);
		}
	}

}
