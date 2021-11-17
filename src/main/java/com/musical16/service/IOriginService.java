package com.musical16.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.musical16.dto.MessageDTO;
import com.musical16.dto.OriginDTO;

public interface IOriginService {

	public List<OriginDTO> findAll();

	public MessageDTO save(OriginDTO originDTO, HttpServletRequest req);

	public MessageDTO delete(long id);
}
