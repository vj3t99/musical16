package com.musical16.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.musical16.dto.rate.RateDTO;
import com.musical16.dto.request.InputRate;
import com.musical16.dto.request.InputRateAdmin;
import com.musical16.dto.request.InputRateReply;
import com.musical16.dto.response.Page;

public interface IRateService {

	List<RateDTO> findRateUser(HttpServletRequest req);

	ResponseEntity<?> save(InputRate input, HttpServletRequest req);

	ResponseEntity<?> delete(Long id);

	ResponseEntity<?> saveReply(InputRateReply input, HttpServletRequest req);

	ResponseEntity<?> deleteReply(Long id, HttpServletRequest req);

	List<RateDTO> showAllRate();

	ResponseEntity<?> update(InputRateAdmin input, HttpServletRequest req);

	Page<RateDTO> showRateProduct(Long id, Integer page);

}
