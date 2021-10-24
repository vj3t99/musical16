package com.musical16.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musical16.Entity.RoleEntity;
import com.musical16.repository.RoleRepository;
import com.musical16.service.IRoleService;


@Service(value = "roleService")
public class RoleService implements IRoleService{
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public RoleEntity findByName(String name) {
		RoleEntity role = roleRepository.findRoleByName(name);
        return role;
	}

	
}
