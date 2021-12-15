package com.musical16.converter;


import org.springframework.stereotype.Component;

import com.musical16.Entity.UserEntity;
import com.musical16.dto.request.RegisterDTO;
import com.musical16.dto.response.UserDTO;


@Component
public class UserConverter {


	public UserEntity toEntity(RegisterDTO userDTO) {
		UserEntity userEntity  = new UserEntity();
		userEntity.setUserName(userDTO.getUsername());
		userEntity.setFullName(userDTO.getFullname());
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setAddress(userDTO.getAddress());
		userEntity.setPhone(userDTO.getPhone());
		userEntity.setSex(userDTO.getSex());
		userEntity.setStatus(0);
		return userEntity;
	}
	
	public UserDTO toDTO(UserEntity userEntity) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(userEntity.getId());
		userDTO.setUsername(userEntity.getUserName());
		userDTO.setFullname(userEntity.getFullName());
		userDTO.setEmail(userEntity.getEmail());
		userDTO.setAddress(userEntity.getAddress());
		userDTO.setPhone(userEntity.getPhone());
		userDTO.setUrl(userEntity.getUrl());
		userDTO.setSex(userEntity.getSex());
		userDTO.setStatus(userEntity.getStatus());
		userDTO.setCreatedBy(userEntity.getCreatedBy());
		userDTO.setCreatedDate(userEntity.getCreatedDate());
		userDTO.setModifiedBy(userEntity.getModifiedBy());
		userDTO.setModifiedDate(userEntity.getModifiedDate());
		return userDTO;
	}
}
