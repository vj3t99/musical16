package com.musical16.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.musical16.Entity.RoleEntity;
import com.musical16.Entity.UserEntity;
import com.musical16.dto.request.InputUser;
import com.musical16.dto.request.RegisterUserAdmin;
import com.musical16.dto.response.UserAdminDTO;
import com.musical16.dto.role.RoleDTO;

@Component
public class UserAdminConverter {

	@Autowired
	private RoleConverter roleConverter;
	
	public UserAdminDTO toDTO(UserEntity userEntity) {
		UserAdminDTO userDTO = new UserAdminDTO();
		userDTO.setId(userEntity.getId());
		userDTO.setUsername(userEntity.getUserName());
		userDTO.setFullname(userEntity.getFullName());
		userDTO.setEmail(userEntity.getEmail());
		userDTO.setAddress(userEntity.getAddress());
		userDTO.setPhone(userEntity.getPhone());
		userDTO.setUrl(userEntity.getUrl());
		userDTO.setSex(userEntity.getSex());
		userDTO.setStatus(userEntity.getStatus());
		List<RoleDTO> list = new ArrayList<>();
		for(RoleEntity each : userEntity.getRoles()) {
			list.add(roleConverter.toDTO(each));
		}
		userDTO.setRoles(list);
		userDTO.setCreatedBy(userEntity.getCreatedBy());
		userDTO.setCreatedDate(userEntity.getCreatedDate());
		userDTO.setModifiedBy(userEntity.getModifiedBy());
		userDTO.setModifiedDate(userEntity.getModifiedDate());
		return userDTO;
	}

	public UserEntity toEntity(RegisterUserAdmin input) {
		UserEntity user = new UserEntity();
		user.setUserName(input.getUsername());
		user.setFullName(input.getFullname());
		user.setEmail(input.getEmail());
		user.setAddress(input.getAddress());
		user.setUrl(input.getUrl());
		user.setPhone(input.getPhone());
		user.setSex(input.getSex());
		user.setStatus(input.getStatus());
		return user;
	}

	public UserEntity toEntity(InputUser input, UserEntity old) {
		UserEntity user = old;
		user.setFullName(input.getFullname());
		user.setEmail(input.getEmail());
		user.setAddress(input.getAddress());
		user.setUrl(input.getUrl());
		user.setPhone(input.getPhone());
		user.setSex(input.getSex());
		user.setStatus(input.getStatus());
		return user;
	}

	
}
