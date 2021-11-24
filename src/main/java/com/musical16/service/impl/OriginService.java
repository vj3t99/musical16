package com.musical16.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musical16.Entity.OriginEntity;
import com.musical16.converter.OriginConverter;
import com.musical16.dto.product.OriginDTO;
import com.musical16.dto.response.MessageDTO;
import com.musical16.repository.OriginRepository;
import com.musical16.service.IOriginService;

@Service
public class OriginService implements IOriginService{
	
	@Autowired
	private OriginRepository originRepository;

	@Autowired
	private OriginConverter originConverter;
	
	@Autowired
	private HelpService helpService;
	
	@Override
	public List<OriginDTO> findAll() {
		List<OriginEntity> origin = originRepository.findAll();
		List<OriginDTO> originDTO = new ArrayList<>();
		for(OriginEntity each : origin) {
			OriginDTO e = originConverter.toDTO(each);
			originDTO.add(e);
		}
		return originDTO;
	}

	@Override
	public MessageDTO save(OriginDTO originDTO, HttpServletRequest req) {
		MessageDTO message = new MessageDTO();
		OriginEntity origin = new OriginEntity();
		if(originDTO.getId()!=null) {
			if(originRepository.findOne(originDTO.getId())!=null) {
				OriginEntity old = originRepository.findOne(originDTO.getId());
				origin = originConverter.toEntity(originDTO, old);
				origin.setModifiedBy(helpService.getName(req));
				origin.setModifiedDate(new Timestamp(System.currentTimeMillis()));
				originRepository.save(origin);
				message.setMessage("Đã cập nhật thành công xuất xứ "+origin.getName());
			}else {
				message.setMessage("Không tìm thấy mã xuất xứ ");
			}
		}else {
			origin = originConverter.toEntity(originDTO);
			origin.setCreatedBy(helpService.getName(req));
			origin.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			originRepository.save(origin);
			message.setMessage("Đã thêm thành công xuất xứ "+origin.getName());
		}
		return message;
	}

	@Override
	public MessageDTO delete(long id) {
		MessageDTO message = new MessageDTO();
		if(id!=0&&originRepository.findOne(id)!=null) {
			OriginEntity origin = originRepository.findOne(id);
			originRepository.delete(origin);
			message.setMessage("Đã xóa thành công xuất xứ "+origin.getName());
		}else {
			message.setMessage("Không tìm thấy mã xuất xứ");
		}
		return message;
	}

}
