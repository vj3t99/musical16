package com.musical16.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.musical16.dto.request.ChangePassword;
import com.musical16.dto.request.ForgotPasswordDTO;
import com.musical16.dto.request.InputUser;
import com.musical16.dto.request.RegisterDTO;
import com.musical16.dto.request.RegisterUserAdmin;
import com.musical16.dto.request.UpdateUserInfoDTO;
import com.musical16.dto.response.MessageDTO;
import com.musical16.dto.response.Page;
import com.musical16.dto.response.UserAdminDTO;
import com.musical16.dto.response.UserDTO;

public interface IUserService {

    public UserDTO findOne(String username);
	
	public ResponseEntity<?> save(RegisterDTO user, HttpServletRequest req) throws Exception;
	
	public String activation(String token);
	
	public Integer getByStatus(String username);

	public MessageDTO forgotpassword(ForgotPasswordDTO email, HttpServletRequest req);

	public MessageDTO resetpassword(String token);

	public ResponseEntity<?> save(UpdateUserInfoDTO user, HttpServletRequest req) throws Exception;

	public MessageDTO uploadImage(MultipartFile file, HttpServletRequest req);

	public ResponseEntity<?> changePassword(ChangePassword user, HttpServletRequest req);

	public Page<UserAdminDTO> showAll(Integer page);

	public ResponseEntity<?> showOne(Long id);

	public ResponseEntity<?> save(InputUser input, HttpServletRequest req);

	public ResponseEntity<?> insert(RegisterUserAdmin input, HttpServletRequest req);

	public ResponseEntity<?> delete(Long id);
	
}
