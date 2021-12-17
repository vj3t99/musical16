package com.musical16.service.impl;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
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
import com.musical16.converter.UserAdminConverter;
import com.musical16.converter.UserConverter;
import com.musical16.dto.request.ChangePassword;
import com.musical16.dto.request.ForgotPasswordDTO;
import com.musical16.dto.request.InputUser;
import com.musical16.dto.request.RegisterDTO;
import com.musical16.dto.request.RegisterUserAdmin;
import com.musical16.dto.request.UpdateUserInfoDTO;
import com.musical16.dto.response.MessageDTO;
import com.musical16.dto.response.Page;
import com.musical16.dto.response.ResponseDTO;
import com.musical16.dto.response.UserAdminDTO;
import com.musical16.dto.response.UserDTO;
import com.musical16.repository.CartRepository;
import com.musical16.repository.RoleRepository;
import com.musical16.repository.UserRepository;
import com.musical16.service.IHelpService;
import com.musical16.service.IRoleService;
import com.musical16.service.IUserService;


@Service(value = "userService")
public class UserService implements UserDetailsService, IUserService{
	
	@Value("${jpa.page.limit}")
	private Integer PAGE_LIMIT;
	
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
    
//    @Autowired
//    private IFileStorageService fileStorageService;

    @Autowired
    private UserAdminConverter userAdminConverter;
    
    @Autowired
    private RoleRepository roleRepository;
    
	@Override
	public ResponseEntity<?> save(RegisterDTO user, HttpServletRequest req) throws Exception,MethodArgumentNotValidException {
		ResponseDTO<UserDTO> response = new ResponseDTO<>();
		try {
			if(userRepository.findByUserName(user.getUsername())!=null) {
				response.setMessage("Tên đăng nhập đã tồn tại");
				return ResponseEntity.badRequest().body(response);
			}else if(userRepository.findByEmail(user.getEmail())!=null){
				response.setMessage("Email đã tồn tại");
				return ResponseEntity.badRequest().body(response);
			}else {
				UserEntity nUser = userConverter.toEntity(user);
				String image = "default.png";
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
		        String link = "musical16.herokuapp.com" + "/activation?token=" + nUser.getToken();
		         try {
					mailHelp.sendHtmlEmailActive(nUser.getEmail(), link);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
		         userRepository.save(nUser);
		         CreateCart(nUser);
		         response.setMessage("Đăng kí thành công, vui lòng kiểm tra email để kích hoạt tài khoản !");
		         response.setObject(userConverter.toDTO(nUser));
		         return ResponseEntity.ok(response);
			}
		} catch (DataIntegrityViolationException e) {
			response.setMessage("Tên đăng nhập hoặc email đã tồn tại");
			return ResponseEntity.badRequest().body(response);
		}
        
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
		}else {
			message = "mã kích hoạt không tồn tại hoặc tài khoản đã được kích hoạt";
		}
		
		return message;
	}
	
	public void CreateCart(UserEntity user) {
		CartEntity cart = new CartEntity();
		if(cartRepository.findByUser(user)==null) {
			cart.setUser(user);
			cart.setTotalPrice(0.0);
			cart.setTotalQuantity(0);
			cartRepository.save(cart);
		}
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
			message = "Thành công, tài khoản của bạn đã được thay đổi mật khẩu vui lòng kiểm tra mail của bạn để lấy mật khẩu mới";
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
	public ResponseEntity<?> save(UpdateUserInfoDTO user, HttpServletRequest req) {
		ResponseDTO<UserDTO> result = new ResponseDTO<>();
		UserEntity nUser = userRepository.findByUserName(helpService.getName(req));
		if(BCrypt.checkpw(user.getPassword(), nUser.getPassword())) {
			nUser.setFullName(user.getFullname());
			nUser.setUrl(user.getUrl());
			nUser.setSex(user.getSex());
			nUser.setAddress(user.getAddress());
			nUser.setPhone(user.getPhone());
			userRepository.save(nUser);
			result.setMessage("Cập nhật thông tin cá nhân thành công");
			result.setObject(userConverter.toDTO(nUser));
			return ResponseEntity.ok(result);
		}else {
			result.setMessage("Mật khẩu không chính xác");
			return ResponseEntity.badRequest().body(result);
		}
	}


	@Override
	public MessageDTO uploadImage(MultipartFile file, HttpServletRequest req) {
//		String filename = fileStorageService.storeFile(file);
//		String url = helpService.getSiteURL(req)+"/downloadFile/"+filename;
//		UserEntity user = userRepository.findByUserName(helpService.getName(req));
//		if(!user.getImage().equals("default.png")) {
//			fileStorageService.deleteFile(user.getImage());
//		}
//		user.setImage(filename);
//		user.setUrl(url);
//		userRepository.save(user);
//		return new MessageDTO("Thêm ảnh thành công");
		return null;
	}


	@Override
	public ResponseEntity<?> changePassword(ChangePassword user, HttpServletRequest req) {
		ResponseDTO<UserDTO> result = new ResponseDTO<>();
		UserEntity nUser = userRepository.findByUserName(helpService.getName(req));
		if(BCrypt.checkpw(user.getPassword(), nUser.getPassword())) {
			nUser.setPassword(bcryptEncoder.encode(user.getNewPassword()));
			userRepository.save(nUser);
			result.setMessage("Thay đổi mật khẩu thành công");
			result.setObject(userConverter.toDTO(nUser));
			return ResponseEntity.ok(result);
		}else {
			result.setMessage("Mật khẩu cũ không chính xác");
			return ResponseEntity.badRequest().body(result);
		}
	}


	@Override
	public Page<UserAdminDTO> showAll(Integer page) {
		Page<UserAdminDTO> result = new Page<>();
		List<UserAdminDTO> list = new ArrayList<>();
		Integer index ;
		try {
			if(page<=0) {
				index = 1;
			}else {
				index = page;
			}
		} catch (NullPointerException e) {
			index = 1;
		}
		Pageable pageable = new PageRequest(index-1, PAGE_LIMIT, Direction.ASC,"id");
		for(UserEntity each : userRepository.findAll(pageable)) {
			list.add(userAdminConverter.toDTO(each));
		}
		result.setPage(index);
		result.setTotalPage((int) Math.ceil((double)userRepository.findAll().size()/PAGE_LIMIT));
		result.setList(list);
		return result;
	}


	@Override
	public ResponseEntity<?> showOne(Long id) {
		ResponseDTO<UserAdminDTO> result = new ResponseDTO<>();
		UserEntity userEntity = userRepository.findOne(id);		
		if(userEntity!=null) {
			UserAdminDTO user = userAdminConverter.toDTO(userEntity);
			result.setMessage(user.getUsername());
			result.setObject(user);
			return ResponseEntity.ok(result);
		}else {
			result.setMessage("Không tìm thấy hoặc user id không hợp lệ");
			return ResponseEntity.badRequest().body(result);
		}
	}


	@Override
	public ResponseEntity<?> save(InputUser input, HttpServletRequest req) {
		ResponseDTO<UserAdminDTO> result = new ResponseDTO<>();
		try {		
				UserEntity user = userRepository.findOne(input.getId());
				if(user!=null) {
					if(roleRepository.findOne(input.getRole())!=null) {
						user = userAdminConverter.toEntity(input,user);
						user.setPassword(bcryptEncoder.encode(input.getPassword()));
						List<RoleEntity> roles = new ArrayList<>();
						roles.add(roleRepository.findOne(input.getRole()));
						user.setRoles(roles);
						user.setModifiedBy(helpService.getName(req));
						user.setModifiedDate(new Timestamp(System.currentTimeMillis()));
						userRepository.save(user);
						result.setMessage("Cập nhật user thành công !");
						result.setObject(userAdminConverter.toDTO(user));
						return ResponseEntity.ok(result);
					}else {
						result.setMessage("Mã role không tồn tại");
						return ResponseEntity.badRequest().body(result);
					}
				}else {
					result.setMessage("Mã user không tồn tại");
					return ResponseEntity.badRequest().body(result);
				}
		} catch (DataIntegrityViolationException e) {
			result.setMessage("Email đã tồn tại");
			return ResponseEntity.badRequest().body(result);
		}
	}


	@Override
	public ResponseEntity<?> insert(RegisterUserAdmin input, HttpServletRequest req) {
		ResponseDTO<UserAdminDTO> result = new ResponseDTO<>();
		try {
			if(roleRepository.findOne(input.getRole())!=null) {
				UserEntity user = userAdminConverter.toEntity(input);
				user.setPassword(bcryptEncoder.encode(input.getPassword()));
				user.setCreatedBy(helpService.getName(req));
				user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				List<RoleEntity> roles = new ArrayList<>();
				roles.add(roleRepository.findOne(input.getRole()));
				user.setRoles(roles);
				userRepository.save(user);
				if(roleRepository.findOne(input.getRole()).getName().equals("USER")) {
					CreateCart(user);
				}
				result.setMessage("Tạo user thành công !");
				result.setObject(userAdminConverter.toDTO(user));
				return ResponseEntity.ok(result);
			}else {
				result.setMessage("Mã role không tồn tại");
				return ResponseEntity.badRequest().body(result);
			}
		} catch (DataIntegrityViolationException e) {
			result.setMessage("Username hoặc email đã tồn tại");
			return ResponseEntity.badRequest().body(result);
		}
	}


	@Override
	public ResponseEntity<?> delete(Long id) {
		ResponseDTO<UserDTO> result = new ResponseDTO<>();
		UserEntity user = userRepository.findOne(id);
		try {
			if(user!=null) {
				user.setRoles(null);
				userRepository.save(user);
				userRepository.delete(user);
				result.setMessage("Xóa thành công");
				result.setObject(userConverter.toDTO(user));
				return ResponseEntity.ok(result);
			}else {
				result.setMessage("Không tìm thấy hoặc id không hợp lệ");
				return ResponseEntity.badRequest().body(result);
			}
		} catch (DataIntegrityViolationException e) {
			result.setMessage("Không thể thao tác");
			return ResponseEntity.badRequest().body(result);
		}
	}
	
	
}
