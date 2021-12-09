package com.musical16.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.musical16.dto.rate.RateDTO;
import com.musical16.dto.request.InputRate;

public interface IRateService {

	List<RateDTO> findRateUser(HttpServletRequest req);

	ResponseEntity<?> save(InputRate input, HttpServletRequest req);

}
