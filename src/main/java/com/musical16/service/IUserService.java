package com.musical16.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.musical16.dto.ForgotPasswordDTO;
import com.musical16.dto.MessageDTO;
import com.musical16.dto.RegisterDTO;
import com.musical16.dto.UpdateUserInfoDTO;
import com.musical16.dto.UserDTO;

public interface IUserService {

    public UserDTO findOne(String username);
	
	public MessageDTO save(RegisterDTO user, HttpServletRequest req) throws Exception;
	
	public String activation(String token);
	
	public Integer getByStatus(String username);

	public MessageDTO forgotpassword(ForgotPasswordDTO email, HttpServletRequest req);

	public MessageDTO resetpassword(String token);

	public MessageDTO save(UpdateUserInfoDTO user, HttpServletRequest req) throws Exception;

	public MessageDTO uploadImage(MultipartFile file, HttpServletRequest req);
}
