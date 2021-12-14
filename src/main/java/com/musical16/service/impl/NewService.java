package com.musical16.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.musical16.Entity.CategoryNewEntity;
import com.musical16.Entity.NewEntity;
import com.musical16.converter.NewConverter;
import com.musical16.dto.news.NewDTO;
import com.musical16.dto.request.InputNew;
import com.musical16.dto.response.MessageDTO;
import com.musical16.dto.response.Page;
import com.musical16.dto.response.ResponseDTO;
import com.musical16.repository.CategoryNewRepository;
import com.musical16.repository.NewRepository;
import com.musical16.service.IFileStorageService;
import com.musical16.service.IHelpService;
import com.musical16.service.INewService;

@Service
public class NewService implements INewService{
	
	@Value("${jpa.page.limit}")
	private Integer PAGE_LIMIT;
	
	@Autowired
	private NewConverter newConverter;
	
	@Autowired
	private IFileStorageService fileStorageService;
	
	@Autowired
	private NewRepository newRepository;
	
	@Autowired
	private CategoryNewRepository categoryNewRepository;
	
	@Autowired
	private IHelpService helpService;

	@Override
	public Page<NewDTO> findAll(Integer page, String[] sort, Long id) {
		Page<NewDTO> result = new Page<>();
		List<Order> listOrder = new ArrayList<>();
		List<NewDTO> newDTO = new ArrayList<>();
		Integer index;
		try {
			if(page<=0||page==null) {
				index = 1;
			}else {
				index = page;
			}
		} catch (NullPointerException e) {
			index = 1;
		}
		try {
			for(String each : sort) {
				if(each.equals("moi-cu")) {
					listOrder.add(new Order(Direction.DESC, "id"));
				}else if(each.equals("cu-moi")) {
					listOrder.add(new Order(Direction.ASC, "id"));
				}
			}
		} catch (Exception e) {
			listOrder.add(new Order(Direction.DESC, "id"));
		}
		if(listOrder.size()==0) {
			listOrder.add(new Order(Direction.DESC, "id"));
		}
		Sort sorts = new Sort(listOrder);
		Pageable pageable = new PageRequest(index - 1, PAGE_LIMIT , sorts);
		if(id!=null) {
			CategoryNewEntity category = categoryNewRepository.findOne(id);
			for(NewEntity each : newRepository.findByCategoryNews(category, pageable)) {
				newDTO.add(newConverter.toDTO(each));
			}
			result.setTotalPage((int) Math.ceil((double) newRepository.findByCategoryNews(category, pageable).size()/PAGE_LIMIT));
		}else {	
			for(NewEntity each : newRepository.findAll(pageable)) {
				newDTO.add(newConverter.toDTO(each));
			}
			result.setTotalPage((int) Math.ceil((double) newRepository.findAll(pageable).getSize()/PAGE_LIMIT));
		}
		
		result.setPage(index);
		result.setList(newDTO);
		return result;
	}

	@Override
	public ResponseEntity<?> save(InputNew input, HttpServletRequest req) {
		ResponseDTO<NewDTO> result = new ResponseDTO<>();
		NewEntity news = new NewEntity();
		if(input.getId()!=null) {
			if(categoryNewRepository.findOne(input.getCategoryNews())!=null) {
				news = newRepository.findOne(input.getId());
				NewEntity nNew = newConverter.toEntity(input,news);
				CategoryNewEntity category = categoryNewRepository.findOne(input.getCategoryNews());
				nNew.setTitle("["+category.getName()+"]"+" "+nNew.getName());
				nNew.setCategoryNews(category);
				nNew.setModifiedBy(helpService.getName(req));
				nNew.setModifiedDate(new Timestamp(System.currentTimeMillis()));
				newRepository.save(nNew);
				result.setMessage("Cập nhật thành công bài viết " + nNew.getName());
				result.setObject(newConverter.toDTO(nNew));
				return ResponseEntity.ok(result);
			}else {
				result.setMessage("không tìm thấy mã thể loại bài viết : "+input.getCategoryNews());
				return ResponseEntity.badRequest().body(result);
			}
		}else {
			if(categoryNewRepository.findOne(input.getCategoryNews())!=null) {
				news = newConverter.toEntity(input);
				CategoryNewEntity category = categoryNewRepository.findOne(input.getCategoryNews());
				news.setTitle("["+category.getName()+"]"+" "+news.getName());
				news.setCategoryNews(category);
				news.setCreatedBy(helpService.getName(req));
				news.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				newRepository.save(news);
				result.setMessage("Thêm bài viết "+ news.getName() + " thành công");
				result.setObject(newConverter.toDTO(news));
				return ResponseEntity.ok(result);
			}else {
				result.setMessage("không tìm thấy mã thể loại bài viết : "+input.getCategoryNews());
				return ResponseEntity.badRequest().body(result);
			}
		}
	}

	@Override
	public ResponseEntity<?> delete(Long id) {
		ResponseDTO<NewDTO> result = new ResponseDTO<>();
		if(newRepository.findOne(id)!=null) {
			NewEntity news = newRepository.findOne(id);
			newRepository.delete(news);
			result.setMessage("Đã xóa thành công bài viết " + news.getName());
			result.setObject(newConverter.toDTO(news));
			return ResponseEntity.ok(result);
		}else {
			result.setMessage("không tìm thấy mã bài viết : " + id);
			return ResponseEntity.badRequest().body(result);
		}
	}

	@Override
	public NewDTO findOne(Long id) {
		NewEntity news = newRepository.findOne(id);
		return newConverter.toDTO(news);
	}

	@Override
	public MessageDTO uploadImage(MultipartFile file, Long id, HttpServletRequest req) {
		MessageDTO message = new MessageDTO();
		if(newRepository.findOne(id)!=null) {
			String filename = fileStorageService.storeFile(file);
			String url = helpService.getSiteURL(req)+"/downloadFile/"+filename;
			NewEntity news = newRepository.findOne(id);
			news.setUrl(url);
			news.setModifiedBy(helpService.getName(req));
			news.setModifiedDate(new Timestamp(System.currentTimeMillis()));
			newRepository.save(news);
			message.setMessage("Đã upload hình thành công");
		}else {
			message.setMessage("Mã bài viết không tồn tại");
		}
		return message;
	}


	
}
