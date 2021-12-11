package com.musical16.api.admin;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.musical16.dto.news.NewDTO;
import com.musical16.dto.response.MessageDTO;
import com.musical16.dto.response.Page;
import com.musical16.service.INewService;

@RestController
public class NewAPI {
	
	@Autowired
	private INewService newService;
	
	@GetMapping("/new")
	public Page<NewDTO> findAll(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value= "sort", required = false ) String[] sort, 
			@RequestParam(value = "category", required = false) Long id){
		return newService.findAll(page, sort, id);
	}
	
	@GetMapping("/new/{id}")
	public NewDTO findOne(@PathVariable("id") Long id) {
		return newService.findOne(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/new")
	public ResponseEntity<?> save(@RequestBody NewDTO newDTO, HttpServletRequest req) {
		return newService.save(newDTO, req);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/new/{id}")
	public MessageDTO uploadImage(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id, HttpServletRequest req) {
		return newService.uploadImage(file,id,req);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/new/{id}")
	public ResponseEntity<?> delele(@PathVariable("id") Long id) {
		return newService.delete(id);
	}
}
