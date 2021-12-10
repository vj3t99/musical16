package com.musical16.api.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.musical16.dto.product.OriginDTO;
import com.musical16.service.IOriginService;

@RestController
public class OriginAPI {

	@Autowired
	private IOriginService originService;
	
	@GetMapping(value = "/origin")
	public List<OriginDTO> findAll() {
		return originService.findAll();	
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/origin")
	public ResponseEntity<?> save(@Valid @RequestBody OriginDTO categoryDTO, HttpServletRequest req) {
		return originService.save(categoryDTO,req);
		
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/origin/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		 return originService.delete(id);
		
	}
}
