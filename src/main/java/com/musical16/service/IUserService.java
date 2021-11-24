package com.musical16.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.musical16.dto.request.ChangePassword;
import com.musical16.dto.request.ForgotPasswordDTO;
import com.musical16.dto.request.RegisterDTO;
import com.musical16.dto.request.UpdateUserInfoDTO;
import com.musical16.dto.response.MessageDTO;
import com.musical16.dto.response.ResponseDTO;
import com.musical16.dto.response.UserDTO;

public interface IUserService {

    public UserDTO findOne(String username);
	
	public ResponseDTO<UserDTO> save(RegisterDTO user, HttpServletRequest req) throws Exception;
	
	public String activation(String token);
	
	public Integer getByStatus(String username);

	public MessageDTO forgotpassword(ForgotPasswordDTO email, HttpServletRequest req);

	public MessageDTO resetpassword(String token);

	public MessageDTO save(UpdateUserInfoDTO user, HttpServletRequest req) throws Exception;

	public MessageDTO uploadImage(MultipartFile file, HttpServletRequest req);

	public MessageDTO changePassword(ChangePassword user, HttpServletRequest req);
	
}
