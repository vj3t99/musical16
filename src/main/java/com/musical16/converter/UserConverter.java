package com.musical16.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.musical16.Entity.UserEntity;
import com.musical16.dto.RegisterDTO;
import com.musical16.dto.UserDTO;


@Component
public class UserConverter {

	@Autowired
    private BCryptPasswordEncoder bcryptEncoder;

	public UserEntity toEntity(RegisterDTO userDTO) {
		UserEntity userEntity  = new UserEntity();
		userEntity.setUserName(userDTO.getUsername());
		userEntity.setPassword(bcryptEncoder.encode(userDTO.getPassword()));
		userEntity.setFullName(userDTO.getFullname());
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setAddress(userDTO.getAddress());
		userEntity.setPhone(userDTO.getPhone());
		userEntity.setStatus(0);
		return userEntity;
	}
	
	public UserDTO toDTO(UserEntity userEntity) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(userEntity.getId());
		userDTO.setUserName(userEntity.getUserName());
		userDTO.setFullName(userEntity.getFullName());
		userDTO.setEmail(userEntity.getEmail());
		userDTO.setAddress(userEntity.getAddress());
		userDTO.setPhone(userEntity.getPhone());
		userDTO.setImage(userEntity.getImage());
		userDTO.setUrl(userEntity.getUrl());
		userDTO.setCreatedBy(userEntity.getCreatedBy());
		userDTO.setCreatedDate(userEntity.getCreatedDate());
		userDTO.setModifiedBy(userEntity.getModifiedBy());
		userDTO.setModifiedDate(userEntity.getModifiedDate());
		return userDTO;
	}
}
