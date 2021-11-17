package com.musical16.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.musical16.config.TokenProvider;
import com.musical16.dto.ForgotPasswordDTO;
import com.musical16.dto.LoginDTO;
import com.musical16.dto.MessageDTO;
import com.musical16.dto.RegisterDTO;
import com.musical16.dto.TokenDTO;
import com.musical16.dto.UpdateUserInfoDTO;
import com.musical16.dto.UserDTO;
import com.musical16.service.IHelpService;
import com.musical16.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;



@RestController
public class UserAPI {
	
	@Autowired
	private TokenProvider tokenProvider;
	
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IHelpService helpService;
	


    

    @PreAuthorize("hasRole('USER')")
	@GetMapping("/userinfo")
	public UserDTO getUser(HttpServletRequest req) {
    	String username = helpService.getName(req);
		return userService.findOne(username);
	}
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/userinfo")
    public MessageDTO updateUser(@RequestBody UpdateUserInfoDTO user, HttpServletRequest req) throws Exception {
    	return userService.save(user, req);
    }
    
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/userinfo")
    public MessageDTO imageUpload(@RequestParam("file") MultipartFile file, HttpServletRequest req) {
    	return userService.uploadImage(file, req);
    }
	
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> generateToken(@Valid @RequestBody LoginDTO loginUser) throws AuthenticationException {
		TokenDTO tokendto = new TokenDTO();
		try {
			final Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginUser.getUsername(),
	                        loginUser.getPassword()
	                )
	        );
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        final String token = tokenProvider.generateToken(authentication);
	        String name = tokenProvider.getUsernameFromToken(token);
	        if(userService.getByStatus(name)==1) {
	        	tokendto.setToken(token);
	            tokendto.setMessage("Đăng nhập thành công");
	        }else {
	        	tokendto.setMessage("Tài khoản chưa được kích hoạt, vui lòng kiểm tra email của bạn để kích hoạt tài khoản");
	        }
	        
		} catch (InternalAuthenticationServiceException e) {
			tokendto.setMessage("Không tìm thấy Username");
		} catch (BadCredentialsException e2) {
			tokendto.setMessage("Sai password");
		}
		return ResponseEntity.ok(tokendto);
	}
	
	@PostMapping(value="/register")
    public MessageDTO saveUser(@Valid @RequestBody RegisterDTO user, HttpServletRequest req) throws Exception{
        return userService.save(user,req);
	}
	
	@GetMapping(value="/activation")
	public MessageDTO activation(@RequestParam(value="token", required=true) String token){
		MessageDTO message = new MessageDTO();
		message.setMessage(userService.activation(token));
		return message;
	}
	@PostMapping(value="/forgotpassword")
	public MessageDTO forgotpassword(@Valid @RequestBody ForgotPasswordDTO email, HttpServletRequest req) {
		return userService.forgotpassword(email,req);
		
	}
	@GetMapping(value = "/resetpassword")
	public MessageDTO resetpassword(@RequestParam(value = "token",required = true) String token) {
		return userService.resetpassword(token);
		
	}
}
