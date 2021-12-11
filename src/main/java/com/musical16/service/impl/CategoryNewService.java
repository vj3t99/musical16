package com.musical16.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.musical16.Entity.CategoryNewEntity;
import com.musical16.converter.CategoryNewConverter;
import com.musical16.dto.news.CategoryNewDTO;
import com.musical16.dto.response.ResponseDTO;
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
	public ResponseEntity<?> save(CategoryNewDTO categoryNew, HttpServletRequest req) {
		ResponseDTO<CategoryNewDTO> result = new ResponseDTO<>();
		CategoryNewEntity category = new CategoryNewEntity();
		if(categoryNew.getId()!=null) {
			category = categoryNewRepository.findOne(categoryNew.getId());
			CategoryNewEntity newCategoryNew = categoryNewConverter.toEntity(categoryNew, category);
			newCategoryNew.setModifiedBy(helpService.getName(req));
			newCategoryNew.setModifiedDate(new Timestamp(System.currentTimeMillis()));
			categoryNewRepository.save(newCategoryNew);
			result.setMessage("Cập nhật danh mục bài viết thành công");
			result.setObject(categoryNewConverter.toDTO(newCategoryNew));
			return ResponseEntity.ok(result);
			
		}else {
			category = categoryNewConverter.toEntity(categoryNew);
			category.setCreatedBy(helpService.getName(req));
			category.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			categoryNewRepository.save(category);
			result.setMessage("Thêm mới danh mục bài viết thành công"); 
			result.setObject(categoryNewConverter.toDTO(category));
			return ResponseEntity.ok(result);
		}
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
	public ResponseEntity<?> delete(Long id) {
		ResponseDTO<CategoryNewDTO> result = new ResponseDTO<>();
		if(categoryNewRepository.findOne(id)!=null) {
			CategoryNewEntity entity = categoryNewRepository.findOne(id);
			categoryNewRepository.delete(entity);
			result.setMessage("Đã xóa thành công danh mục bài viết " + entity.getName());
			result.setObject(categoryNewConverter.toDTO(entity));
			return ResponseEntity.ok(result);
		}else {
			result.setMessage("Không tìm thấy mã danh mục bài viết : ");
			return ResponseEntity.badRequest().body(result);
		}
		
	}

	
}
