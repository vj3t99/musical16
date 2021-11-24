package com.musical16.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musical16.Entity.CategoryNewEntity;
import com.musical16.converter.CategoryNewConverter;
import com.musical16.dto.news.CategoryNewDTO;
import com.musical16.dto.response.MessageDTO;
import com.musical16.repository.CategoryNewRepository;
import com.musical16.service.ICategoryNewService;
import com.musical16.service.IHelpService;

@Service
public class CategoryNewService implements ICategoryNewService{
	
	@Autowired
	private CategoryNewRepository categoryNewRepository;
	
	@Autowired
	private CategoryNewConverter categoryNewConverter;
	
	@Autowired
	private IHelpService helpService;

	@Override
	public MessageDTO save(CategoryNewDTO categoryNew, HttpServletRequest req) {
		MessageDTO message = new MessageDTO();
		CategoryNewEntity category = new CategoryNewEntity();
		if(categoryNew.getId()!=null) {
			category = categoryNewRepository.findOne(categoryNew.getId());
			if(categoryNewRepository.findByCode(categoryNew.getCode())!=null) {
				message.setMessage("Mã code đã tồn tại. Vui lòng thử mã code khác");
			}else {
				CategoryNewEntity newCategoryNew = categoryNewConverter.toEntity(categoryNew, category);
				newCategoryNew.setModifiedBy(helpService.getName(req));
				newCategoryNew.setModifiedDate(new Timestamp(System.currentTimeMillis()));
				categoryNewRepository.save(newCategoryNew);
				message.setMessage("Cập nhật danh mục bài viết thành công");
			}
			
		}else {
			category = categoryNewConverter.toEntity(categoryNew);
			if(categoryNewRepository.findByCode(categoryNew.getCode())!=null) {
				message.setMessage(" Mã code đã tồn tại. Vui lòng thử mã code khác"); 
			}else {
				category.setCreatedBy(helpService.getName(req));
				category.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				categoryNewRepository.save(category);
				message.setMessage("Thêm mới danh mục bài viết thành công"); 
			}
		}
		return message;
	}

	@Override
	public List<CategoryNewDTO> findAll() {
		List<CategoryNewEntity> list = categoryNewRepository.findAll();
		List<CategoryNewDTO> dto = new ArrayList<>();
		for(CategoryNewEntity each : list) {
			CategoryNewDTO e = categoryNewConverter.toDTO(each);
			dto.add(e);
		}
		return dto;
	}

	@Override
	public MessageDTO delete(Long id) {
		MessageDTO message = new MessageDTO();
		if(categoryNewRepository.findOne(id)!=null) {
			CategoryNewEntity entity = categoryNewRepository.findOne(id);
			categoryNewRepository.delete(entity);
			message.setMessage("Đã xóa thành công danh mục bài viết " + entity.getName());
		}else {
			message.setMessage("Không tìm thấy mã danh mục bài viết : ");
		}
		
		return message;
	}

	
}
