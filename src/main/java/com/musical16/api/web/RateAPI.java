package com.musical16.api.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.musical16.dto.rate.RateDTO;
import com.musical16.dto.request.InputRate;
import com.musical16.service.IRateService;

@RestController
public class RateAPI {
	
	@Autowired
	private IRateService rateService;

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
}
