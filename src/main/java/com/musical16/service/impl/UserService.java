package com.musical16.service.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;

import com.musical16.Entity.CartEntity;
import com.musical16.Entity.RoleEntity;
import com.musical16.Entity.UserEntity;
import com.musical16.converter.UserConverter;
import com.musical16.dto.request.ChangePassword;
import com.musical16.dto.request.ForgotPasswordDTO;
import com.musical16.dto.request.RegisterDTO;
import com.musical16.dto.request.UpdateUserInfoDTO;
import com.musical16.dto.response.MessageDTO;
import com.musical16.dto.response.ResponseDTO;
import com.musical16.dto.response.UserDTO;
import com.musical16.repository.CartRepository;
import com.musical16.repository.UserRepository;
import com.musical16.service.IFileStorageService;
import com.musical16.service.IHelpService;
import com.musical16.service.IRoleService;
import com.musical16.service.IUserService;


@Service(value = "userService")
public class UserService implements UserDetailsService, IUserService{
	
	@Autowired
    private IRoleService roleService;

    @Autowired
    private UserRepository userRepository;
    
	@Autowired
	private CartRepository cartRepository;
    
    @Autowired
    private UserConverter userConverter;
    
    @Autowired
    private IHelpService helpService;
    
    @Autowired
    private MailHelp mailHelp;
    
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;
    
    @Autowired
    private IFileStorageService fileStorageService;


	@Override
	public ResponseDTO<UserDTO> save(RegisterDTO user, HttpServletRequest req) throws Exception,MethodArgumentNotValidException {
		ResponseDTO<UserDTO> response = new ResponseDTO<>();
		try {
			if(userRepository.findByUserName(user.getUsername())!=null) {
				response.setMessage("Username đã tồn tại");
			}else if(userRepository.findByEmail(user.getEmail())!=null){
				response.setMessage("Email đã tồn tại");
			}else {
				UserEntity nUser = userConverter.toEntity(user);
				String image = "default.png";
				nUser.setImage(image);
				nUser.setUrl(helpService.getSiteURL(req)+"/downloadFile/"+image);
		        RoleEntity role = roleService.findByName("USER");
		        nUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		        nUser.setCreatedBy(nUser.getUserName());
		        nUser.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
		        List<RoleEntity> roleSet = new ArrayList<>();
		        roleSet.add(role);
//		        if(nUser.getEmail().split("@")[1].equals("admin.edu")){
//		            role = roleService.findByName("ADMIN");
//		            roleSet.add(role);
//		        }
//		        UUID token = UUID.randomUUID();
//				nUser.setToken(token.toString());
		        String token = RandomStringUtils.random(20, true, true);
		        nUser.setToken(token);
		        nUser.setRoles(roleSet);
		        String link = helpService.getSiteURL(req) + "/activation?token=" + nUser.getToken();
		         try {
					mailHelp.sendHtmlEmailActive(nUser.getEmail(), link);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
		         userRepository.save(nUser);
		         response.setMessage("Đăng kí thành công, vui lòng kiểm tra email để kích hoạt tài khoản !");
		         response.setObject(userConverter.toDTO(nUser));
			}
		} catch (DataIntegrityViolationException e) {
			response.setMessage("Username hoặc email đã tồn tại");
		}
        return response;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUserName(username);
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), getAuthority(user));
	}
	private Collection<? extends GrantedAuthority> getAuthority(UserEntity user) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
	}

	@Override
	public String activation(String token) {
		String message ;
		UserEntity user = userRepository.findByToken(token);
		if(user!=null&&user.getStatus().equals(0)) {
			message = "kích hoạt thành công";
			user.setToken("");
			user.setStatus(1);
			userRepository.save(user);
			CartEntity cart = new CartEntity();
			cart.setUser(user);
			cart.setTotalPrice(0.0);
			cart.setTotalQuantity(0);
			cartRepository.save(cart);
			
		}else {
			message = "mã kích hoạt không tồn tại hoặc tài khoản đã được kích hoạt";
		}
		
		return message;
	}

	@Override
	public Integer getByStatus(String username) {
		UserEntity user = userRepository.findByUserName(username);
		return user.getStatus();
	}

	@Override
	public MessageDTO forgotpassword(ForgotPasswordDTO email,HttpServletRequest req) {
		String message;
		UserEntity user = userRepository.findByEmail(email.getEmail());
		if(user!=null) {
			if(user.getStatus()==1) {
//				UUID token = UUID.randomUUID();
//				user.setToken(token.toString());
		        String token = RandomStringUtils.random(20, true, true);
		        user.setToken(token);
				userRepository.save(user);
				String link = helpService.getSiteURL(req) + "/resetpassword?token=" + user.getToken();
				try {
					mailHelp.sendHtmlEmailForgotPassword(email.getEmail(), link);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				message = "Thành công, vui lòng kiểm tra email của bạn để nhận link reset mật khẩu !";
			}else {
				message = "Tài khoản chưa được kích hoạt, vui lòng kích hoạt tài khoản trước !";
			}
		}else {
			message = "Email không hợp lệ !";
		}
		
		return new MessageDTO(message);
	}

	@Override
	public MessageDTO resetpassword(String token) {
		String message;
		UserEntity user = userRepository.findByToken(token);
		if(user!=null&&user.getStatus().equals(1)) {
			String password = RandomStringUtils.random(10, true, true);
			user.setPassword(bcryptEncoder.encode(password));
			userRepository.save(user);
			try {
				mailHelp.sendHtmlEmailPassword(user.getEmail(), password);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			message = "Thành công, tài khoản của ban đã được reset password vui lòng kiểm tra mail của bạn để lấy password mới";
		}else {
			message = "Link không hợp lệ hoặc tài khoản chưa được kích hoạt";
		}
		return new MessageDTO(message);
	}


	@Override
	public UserDTO findOne(String username) {
		UserEntity user = userRepository.findByUserName(username);
		return userConverter.toDTO(user);
	}


	@Override
	public MessageDTO save(UpdateUserInfoDTO user, HttpServletRequest req) {
		UserEntity nUser = userRepository.findByUserName(helpService.getName(req));
		String message;
		if(BCrypt.checkpw(user.getPassword(), nUser.getPassword())) {
			nUser.setFullName(user.getFullname());
			nUser.setSex(user.getSex());
			nUser.setAddress(user.getAddress());
			nUser.setPhone(user.getPhone());
			userRepository.save(nUser);
			message = "Cập nhật thông tin thành công";
		}else {
			message = "Mật khẩu không đúng";
		}
		return new MessageDTO(message);
	}


	@Override
	public MessageDTO uploadImage(MultipartFile file, HttpServletRequest req) {
		String filename = fileStorageService.storeFile(file);
		String url = helpService.getSiteURL(req)+"/downloadFile/"+filename;
		UserEntity user = userRepository.findByUserName(helpService.getName(req));
		if(!user.getImage().equals("default.png")) {
			fileStorageService.deleteFile(user.getImage());
		}
		user.setImage(filename);
		user.setUrl(url);
		userRepository.save(user);
		return new MessageDTO("Thêm ảnh thành công");
	}


	@Override
	public MessageDTO changePassword(ChangePassword user, HttpServletRequest req) {
		UserEntity nUser = userRepository.findByUserName(helpService.getName(req));
		String message;	
		if(BCrypt.checkpw(user.getPassword(), nUser.getPassword())) {
			nUser.setPassword(bcryptEncoder.encode(user.getNewPassword()));
			userRepository.save(nUser);
			message = "Thay đổi mật khẩu thành công";
		}else {
			message = "Mật khẩu cũ không chính xác";
		}
		return new MessageDTO(message);
	}

}
