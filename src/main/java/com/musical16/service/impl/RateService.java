package com.musical16.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.musical16.Entity.ProductEntity;
import com.musical16.Entity.RateEntity;
import com.musical16.Entity.UserEntity;
import com.musical16.converter.RateConverter;
import com.musical16.dto.rate.RateDTO;
import com.musical16.dto.request.InputRate;
import com.musical16.dto.response.ResponseDTO;
import com.musical16.repository.ProductRepository;
import com.musical16.repository.RateRepository;
import com.musical16.repository.UserRepository;
import com.musical16.service.IHelpService;
import com.musical16.service.IRateService;

@Service
public class RateService implements IRateService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RateRepository rateRepository;
	
	@Autowired
	private IHelpService helpService;
	
	@Autowired
	private RateConverter rateConverter;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<RateDTO> findRateUser(HttpServletRequest req) {
		List<RateDTO> result = new ArrayList<>();
		UserEntity user = userRepository.findByUserName(helpService.getName(req));
		for(RateEntity each : rateRepository.findByUserAndFlag(user, false)) {
			RateDTO rateDTO = rateConverter.toDTO(each);
			result.add(rateDTO);
		}
		return result;
	}

	@Override
	public ResponseEntity<?> save(InputRate input, HttpServletRequest req) {
		ResponseDTO<RateDTO> result = new ResponseDTO<>();
		UserEntity user = userRepository.findByUserName(helpService.getName(req));
		RateEntity old = rateRepository.findByIdAndUser(input.getId(), user);
		if(old!=null) {
			if(!old.getFlag()) {
				old = rateConverter.toEntity(input,old);
				old.setCreatedBy(user.getUserName());
				old.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				rateRepository.save(old);
				
				//Tinh diem trung binh cho san pham
				RateUpdate(old.getProduct());
				
				result.setObject(rateConverter.toDTO(old));
				result.setMessage("Đánh giá thành công !");
				return ResponseEntity.ok(result);
			}else {
				result.setMessage("Mã đánh giá đã được sử dụng");
				return ResponseEntity.badRequest().body(result); 
			}
		}else {
			result.setMessage("Không tìm thấy đánh giá !");
			return ResponseEntity.badRequest().body(result);
		}
	}
	
	public void RateUpdate(ProductEntity product) {
		Integer point = 0;
		for(RateEntity each : rateRepository.findByProductAndFlag(product, true)) {
			point += each.getPoint();
		}
		product.setRate_point((double) point/rateRepository.findByProductAndFlag(product, true).size());
		productRepository.save(product);
	}

}
