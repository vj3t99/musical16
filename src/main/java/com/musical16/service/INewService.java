package com.musical16.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.musical16.dto.news.NewDTO;
import com.musical16.dto.request.InputNew;
import com.musical16.dto.response.MessageDTO;
import com.musical16.dto.response.Page;

public interface INewService {

	Page<NewDTO> findAll(Integer page, String[] sort, Long id);

	ResponseEntity<?> save(InputNew input, HttpServletRequest req);

	ResponseEntity<?> delete(Long id);

	NewDTO findOne(Long id);

	MessageDTO uploadImage(MultipartFile file, Long id, HttpServletRequest req);

	List<NewDTO> showAll();


}
