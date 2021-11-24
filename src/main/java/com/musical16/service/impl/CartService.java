package com.musical16.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musical16.Entity.CartDetailEntity;
import com.musical16.Entity.CartEntity;
import com.musical16.Entity.ProductEntity;
import com.musical16.Entity.UserEntity;
import com.musical16.converter.CartDetailConverter;
import com.musical16.dto.cart.CartDTO;
import com.musical16.dto.cart.CartDetailDTO;
import com.musical16.dto.response.MessageDTO;
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
	public MessageDTO save(CartDetailDTO cartDetailDTO, HttpServletRequest req) {
		MessageDTO message = new MessageDTO();
		try {
			UserEntity user = userRepository.findByUserName(helpService.getName(req));
			CartEntity cart = cartRepository.findByUser(user);
			CartDetailEntity cartDetail = new CartDetailEntity();		
			ProductEntity product = productRepository.findOne(cartDetailDTO.getProductId());
			
			//Nếu không có id chi tiết giỏ hàng thì thêm mới
			if (cartDetailDTO.getId() == null) {
				List<CartDetailEntity> listCartDetail = cartDetailRepository.findByCartAndProduct(cart, product);
				//Nếu sản phẩm đã tồn tại trong giỏ hàng thì cộng thêm số lượng
				if (listCartDetail.size()==1) {
					for(CartDetailEntity each : listCartDetail)
						cartDetail = each;
					cartDetail.setQuantity(cartDetailDTO.getQuantity()+cartDetail.getQuantity());
					//Kiểm tra số lượng của giỏ hàng với số lượng của kho hàng đang có
					if (cartDetail.getQuantity()+cartDetailDTO.getQuantity() <= product.getQuantity()) {
						cartDetail.setPrice(product.getPrice() * cartDetail.getQuantity());
						cartDetail.setModifiedBy(helpService.getName(req));
						cartDetail.setModifiedDate(new Timestamp(System.currentTimeMillis()));
						cartDetailRepository.save(cartDetail);
						message.setMessage("Thêm số lượng vào giỏ hàng thành công");
						updateCart(cart);
					} else {
						message.setMessage("Shop chúng tôi không có đủ số lượng để cung cấp");
					}
				// Nếu sản phẩm không tồn tại trong giỏ hàng thì tạo 1 sản phẩm mới trong giỏ hàng
				} else {
					cartDetail.setCart(cart);
					cartDetail.setProduct(product);
					cartDetail.setQuantity(cartDetailDTO.getQuantity());
					//Kiểm tra số lượng của giỏ hàng với số lượng của kho hàng đang có
					if (cartDetailDTO.getQuantity() <= product.getQuantity()) {
						cartDetail.setPrice(product.getPrice() * cartDetail.getQuantity());
						cartDetail.setCreatedBy(helpService.getName(req));
						cartDetail.setCreatedDate(new Timestamp(System.currentTimeMillis()));
						cartDetailRepository.save(cartDetail);
						message.setMessage("Thêm sản phẩm vào giỏ hàng thành công");
						updateCart(cart);
					} else {
						message.setMessage("Shop chúng tôi không có đủ số lượng để cung cấp");
					}
				}
			//Nếu có id thì sẽ cập nhật số lượng sản phẩm
			} else {
				cartDetail = cartDetailRepository.findOne(cartDetailDTO.getId());		
				cartDetail.setQuantity(cartDetailDTO.getQuantity());
				//Kiểm tra số lượng của giỏ hàng với số lượng của kho hàng đang có
				if (cartDetailDTO.getQuantity() <= cartDetail.getProduct().getQuantity()) {
					cartDetail.setPrice(cartDetail.getProduct().getPrice() * cartDetail.getQuantity());
					cartDetail.setModifiedBy(helpService.getName(req));
					cartDetail.setModifiedDate(new Timestamp(System.currentTimeMillis()));
					cartDetailRepository.save(cartDetail);
					message.setMessage("Cập nhật số lượng sản phẩm thành công");
					updateCart(cart);
				} else {
					message.setMessage("Shop chúng tôi không có đủ hàng để cung cấp");
				}

			}
		} catch (NullPointerException e) {
			message.setMessage("Sản phẩm không tồn tại hoặc giỏ hàng của bạn chưa được tạo");
		}
		return message;
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
	public MessageDTO delete(Long id, HttpServletRequest req) {
		MessageDTO message = new MessageDTO();
		CartEntity cart = cartRepository.findByUser(userRepository.findByUserName(helpService.getName(req)));
		if(cart!=null) {
			List<CartDetailEntity> list = cartDetailRepository.findByCartAndProduct(cart, productRepository.findOne(id));
			if(!list.isEmpty()) {
				for(CartDetailEntity each : list) {
					cartDetailRepository.delete(each);
				}
				message.setMessage("Xóa sản phẩm thành công khỏi giỏ hàng");
			}else {
				message.setMessage("Sản phẩm này không có trong giỏ hàng");
			}
			updateCart(cart);
		}else {
			message.setMessage("Giỏ hàng không tồn tại");
		}
		return message;
	}

}
