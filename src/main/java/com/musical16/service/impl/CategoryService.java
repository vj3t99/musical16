package com.musical16.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musical16.Entity.CategoryEntity;
import com.musical16.converter.CategoryConverter;
import com.musical16.dto.product.CategoryDTO;
import com.musical16.dto.response.MessageDTO;
import com.musical16.repository.CategoryRepository;
import com.musical16.service.ICategoryService;
import com.musical16.service.IHelpService;

@Service
public class CategoryService implements ICategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryConverter categoryConverter;
	
	@Autowired
	private IHelpService helpService;

	@Override
	public List<CategoryDTO> findAll() {
		List<CategoryEntity> category = categoryRepository.findAll();
		List<CategoryDTO> categoryDTO = new ArrayList<>();
		for(CategoryEntity each : category) {
			CategoryDTO e = categoryConverter.toDTO(each);
			categoryDTO.add(e);
		}
		return categoryDTO;
	}

	@Override
	public MessageDTO save(CategoryDTO categoryDTO, HttpServletRequest req) {
		MessageDTO message = new MessageDTO();
		try {
			CategoryEntity category = new CategoryEntity();
			if(categoryDTO.getId()!=null) {
				CategoryEntity old = categoryRepository.findOne(categoryDTO.getId());
				category = categoryConverter.toEntity(categoryDTO, old);
				category.setModifiedBy(helpService.getName(req));
				category.setModifiedDate(new Timestamp(System.currentTimeMillis()));
				categoryRepository.save(category);
				message.setMessage("Cập nhật thành công danh mục "+category.getName());
			}else {
				category = categoryConverter.toEntity(categoryDTO);
				category.setCreatedBy(helpService.getName(req));
				category.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				categoryRepository.save(category);
				message.setMessage("Thêm thành công danh mục "+category.getName());
			}
		} catch (NullPointerException e) {
			message.setMessage("Không tìm thấy mã danh mục");
		}
		return message;
	}

	@Override
	public MessageDTO delete(long id) {
		MessageDTO message = new MessageDTO();
		if(id!=0&&categoryRepository.findOne(id)!=null) {
			CategoryEntity category = categoryRepository.findOne(id);
			categoryRepository.delete(category);
			message.setMessage("Đã xóa thành công danh mục "+category.getName());
		}else {
			message.setMessage("Không tìm thấy mã danh mục");
		}
		return message;
	}

}
