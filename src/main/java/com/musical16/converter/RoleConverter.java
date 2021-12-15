package com.musical16.converter;


import org.springframework.stereotype.Component;

import com.musical16.Entity.RoleEntity;
import com.musical16.dto.role.RoleDTO;

@Component
public class RoleConverter {

	public RoleDTO toDTO(RoleEntity roles) {
		RoleDTO role = new RoleDTO();
		role.setId(roles.getId());
		role.setName(roles.getName());
		role.setCode(roles.getCode());
		return role;
	}

}
