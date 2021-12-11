package com.musical16.api.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.musical16.dto.rate.RateDTO;
import com.musical16.dto.request.InputRate;
import com.musical16.dto.request.InputRateReply;
import com.musical16.service.IRateService;

@RestController
public class RateAPI {
	
	@Autowired
	private IRateService rateService;

	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping("/admin/rate")
	public List<RateDTO> showAllRate(){
		return rateService.showAllRate();
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/rate")
	public List<RateDTO> showRateUser(HttpServletRequest req){
		return rateService.findRateUser(req);
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/rate")
	public ResponseEntity<?> save(@RequestBody InputRate input, HttpServletRequest req){
		return rateService.save(input, req);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@DeleteMapping("/rate/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
		return rateService.delete(id);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PostMapping("/rateReply")
	public ResponseEntity<?> saveReply(@RequestBody InputRateReply input, HttpServletRequest req){
		return rateService.saveReply(input, req);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@DeleteMapping("/rateReply/{id}")
	public ResponseEntity<?> deleteReply(@PathVariable("id") Long id, HttpServletRequest req){
		return rateService.deleteReply(id, req);
	}
	
}
