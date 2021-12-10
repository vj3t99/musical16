package com.musical16.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.musical16.dto.product.OriginDTO;

public interface IOriginService {

	public List<OriginDTO> findAll();

	public ResponseEntity<?> save(OriginDTO originDTO, HttpServletRequest req);

	public ResponseEntity<?> delete(long id);
}
