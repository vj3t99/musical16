package com.musical16.service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.musical16.dto.news.NewDTO;
import com.musical16.dto.response.MessageDTO;
import com.musical16.dto.response.Page;

public interface INewService {

	Page<NewDTO> findAll(Integer page, String[] sort, Long id);

	MessageDTO save(NewDTO newDTO, HttpServletRequest req);

	MessageDTO delete(Long id);

	NewDTO findOne(Long id);

	MessageDTO uploadImage(MultipartFile file, Long id, HttpServletRequest req);


}
