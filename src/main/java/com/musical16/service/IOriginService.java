package com.musical16.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.musical16.dto.product.OriginDTO;
import com.musical16.dto.response.MessageDTO;

public interface IOriginService {

	public List<OriginDTO> findAll();

	public MessageDTO save(OriginDTO originDTO, HttpServletRequest req);

	public MessageDTO delete(long id);
}
