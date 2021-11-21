package com.musical16.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musical16.Entity.RoleEntity;
import com.musical16.repository.RoleRepository;

@RestController
public class RoleAPI {
	
	@Autowired
	private RoleRepository roleRepository;

	@GetMapping("/roles")
	public void CreateRoles() {
		RoleEntity role1 = new RoleEntity();
		role1.setName("ADMIN");
		role1.setCode("ADMIN");
		roleRepository.save(role1);
		RoleEntity role2 = new RoleEntity();
		role2.setName("MANAGER");
		role2.setCode("MANAGER");
		roleRepository.save(role2);
		RoleEntity role3 = new RoleEntity();
		role3.setName("USER");
		role3.setCode("USER");
		roleRepository.save(role3);
	}
}
