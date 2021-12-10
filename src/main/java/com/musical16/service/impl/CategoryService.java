package com.musical16.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.musical16.Entity.CategoryEntity;
import com.musical16.converter.CategoryConverter;
import com.musical16.dto.product.CategoryDTO;
import com.musical16.dto.response.ResponseDTO;
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
	public ResponseEntity<?> save(CategoryDTO categoryDTO, HttpServletRequest req) {
		ResponseDTO<CategoryDTO> result = new ResponseDTO<>();
		try {
			CategoryEntity category = new CategoryEntity();
			if(categoryDTO.getId()!=null) {
				CategoryEntity old = categoryRepository.findOne(categoryDTO.getId());
				category = categoryConverter.toEntity(categoryDTO, old);
				category.setModifiedBy(helpService.getName(req));
				category.setModifiedDate(new Timestamp(System.currentTimeMillis()));
				categoryRepository.save(category);
				result.setMessage("Cập nhật thành công danh mục "+category.getName());
				result.setObject(categoryConverter.toDTO(category));
				return ResponseEntity.ok(result);
			}else {
				category = categoryConverter.toEntity(categoryDTO);
				category.setCreatedBy(helpService.getName(req));
				category.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				categoryRepository.save(category);
				result.setMessage("Thêm thành công danh mục "+category.getName());
				result.setObject(categoryConverter.toDTO(category));
				return ResponseEntity.ok(result);
			}
		} catch (NullPointerException e) {
			result.setMessage("Không tìm thấy mã danh mục !");
			return ResponseEntity.badRequest().body(result);
		} catch (DataIntegrityViolationException e2) {
			result.setMessage("Tên danh mục đã tồn tại !");
			return ResponseEntity.badRequest().body(result);
		}
	}

	@Override
	public ResponseEntity<?> delete(long id) {
		ResponseDTO<CategoryDTO> result = new ResponseDTO<>();
		if(id!=0&&categoryRepository.findOne(id)!=null) {
			CategoryEntity category = categoryRepository.findOne(id);
			categoryRepository.delete(category);
			result.setMessage("Đã xóa thành công danh mục "+category.getName());
			result.setObject(categoryConverter.toDTO(category));
			return ResponseEntity.ok(result);
		}else {
			result.setMessage("Không tìm thấy mã danh mục");
			return ResponseEntity.badRequest().body(result);
		}
	}

}
