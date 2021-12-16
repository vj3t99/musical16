package com.musical16.api.web;


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
import com.musical16.converter.UserConverter;
import com.musical16.dto.request.ChangePassword;
import com.musical16.dto.request.ForgotPasswordDTO;
import com.musical16.dto.request.InputUser;
import com.musical16.dto.request.LoginDTO;
import com.musical16.dto.request.RegisterDTO;
import com.musical16.dto.request.RegisterUserAdmin;
import com.musical16.dto.request.UpdateUserInfoDTO;
import com.musical16.dto.response.MessageDTO;
import com.musical16.dto.response.Page;
import com.musical16.dto.response.TokenDTO;
import com.musical16.dto.response.UserAdminDTO;
import com.musical16.dto.response.UserDTO;
import com.musical16.repository.UserRepository;
import com.musical16.service.IHelpService;
import com.musical16.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;



@RestController
public class UserAPI {
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IHelpService helpService;
	


    

    @PreAuthorize("hasAnyRole('USER','ADMIN','MANAGER')")
	@GetMapping("/userinfo")
	public UserDTO getUser(HttpServletRequest req) {
    	String username = helpService.getName(req);
		return userService.findOne(username);
	}
    
    @PreAuthorize("hasAnyRole('USER','ADMIN','MANAGER')")
    @PostMapping("/userinfo")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserInfoDTO user, HttpServletRequest req) throws Exception {
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
	            tokendto.setUser(userConverter.toDTO(userRepository.findByUserName(name)));
	            return ResponseEntity.ok(tokendto);
	        }else {
	        	tokendto.setMessage("Tài khoản chưa kích hoạt, vui lòng kiểm tra email để kích hoạt");
	        	return ResponseEntity.badRequest().body(tokendto);
	        }
	        
		} catch (InternalAuthenticationServiceException e) {
			tokendto.setMessage("Thông tin đăng nhập không hợp lệ");
			return ResponseEntity.badRequest().body(tokendto);
		} catch (BadCredentialsException e2) {
			tokendto.setMessage("Thông tin đăng nhập không hợp lệ");
			return ResponseEntity.badRequest().body(tokendto);
		}
	}
	
	@PostMapping(value="/register")
    public ResponseEntity<?> saveUser(@Valid @RequestBody RegisterDTO user, HttpServletRequest req) throws Exception{
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
	@PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
	@PostMapping(value = "/changepassword")
	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassword user, HttpServletRequest req ) {
		return userService.changePassword(user,req);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/user")
	public Page<UserAdminDTO> showAll(@RequestParam(value = "page", required = false) Integer page){
		return userService.showAll(page);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/user/{id}")
	public ResponseEntity<?> showOne(@PathVariable(value = "id", required = true) Long id ){
		return userService.showOne(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/admin/user")
	public ResponseEntity<?> saveUser(@Valid @RequestBody InputUser input, HttpServletRequest req){
		return userService.save(input, req);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin/user")
	public ResponseEntity<?> insertUser(@Valid @RequestBody RegisterUserAdmin input, HttpServletRequest req){
		return userService.insert(input, req);
	}
	
	@DeleteMapping("/admin/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
		return userService.delete(id);
	}
	
}
