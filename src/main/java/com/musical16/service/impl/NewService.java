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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.musical16.Entity.CategoryNewEntity;
import com.musical16.Entity.NewEntity;
import com.musical16.converter.NewConverter;
import com.musical16.dto.news.NewDTO;
import com.musical16.dto.response.MessageDTO;
import com.musical16.dto.response.Page;
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
	public MessageDTO save(NewDTO newDTO, HttpServletRequest req) {
		MessageDTO message = new MessageDTO();
		NewEntity news = new NewEntity();
		if(newDTO.getId()!=null) {
			if(categoryNewRepository.findOne(newDTO.getCategoryNews())!=null) {
				news = newRepository.findOne(newDTO.getId());
				NewEntity nNew = newConverter.toEntity(newDTO,news);
				nNew.setTitle("["+nNew.getCategoryNews().getName()+"] "+nNew.getName());
				nNew.setModifiedBy(helpService.getName(req));
				nNew.setModifiedDate(new Timestamp(System.currentTimeMillis()));
				newRepository.save(nNew);
				message.setMessage("Cập nhật thành công bài viết " + nNew.getName());
			}else {
				message.setMessage("không tìm thấy mã thể loại bài viết : "+newDTO.getCategoryNews());
			}
		}else {
			if(categoryNewRepository.findOne(newDTO.getCategoryNews())!=null) {
				news = newConverter.toEntity(newDTO);
				news.setCategoryNews(categoryNewRepository.findOne(newDTO.getCategoryNews()));
				String image = "default.png";
				news.setImage(image);
				news.setUrl(helpService.getSiteURL(req)+"/downloadFile/"+image);
				news.setTitle("["+news.getCategoryNews().getName()+"] "+news.getName());
				news.setCreatedBy(helpService.getName(req));
				news.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				newRepository.save(news);
				message.setMessage("Thêm bài viết "+ news.getName() + " thành công");
			}else {
				message.setMessage("không tìm thấy mã thể loại bài viết : "+newDTO.getCategoryNews());
			}
		}
		return message;
	}

	@Override
	public MessageDTO delete(Long id) {
		MessageDTO message  = new MessageDTO();
		if(newRepository.findOne(id)!=null) {
			NewEntity news = newRepository.findOne(id);
			newRepository.delete(news);
			message.setMessage("Đã xóa thành công bài viết " + news.getName());
		}else {
			message.setMessage("không tìm thấy mã bài viết : " + id);
		}
		return message;
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
			if(!news.getImage().equals("default.png")) {	
				fileStorageService.deleteFile(news.getImage());
			}
			news.setImage(filename);
			news.setUrl(url);
			news.setModifiedBy(helpService.getName(req));
			news.setModifiedDate(new Timestamp(System.currentTimeMillis()));
			newRepository.save(news);
			message.setMessage("Đã upload hình "+news.getImage() +" thành công");
		}else {
			message.setMessage("Mã bài viết không tồn tại");
		}
		return message;
	}


	
}
