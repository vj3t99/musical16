package com.musical16.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.musical16.Entity.NewEntity;
import com.musical16.converter.NewConverter;
import com.musical16.dto.MessageDTO;
import com.musical16.dto.NewDTO;
import com.musical16.repository.CategoryNewRepository;
import com.musical16.repository.NewRepository;
import com.musical16.service.IFileStorageService;
import com.musical16.service.IHelpService;
import com.musical16.service.INewService;

@Service
public class NewService implements INewService{
	
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
	public List<NewDTO> findAll() {
		List<NewEntity> news = newRepository.findAll();
		List<NewDTO> newDTO = new ArrayList<>();
		for(NewEntity each: news) {
			NewDTO e = newConverter.toDTO(each);
			newDTO.add(e);
			
		}
		return newDTO;
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
