package com.itconsulting.mentalHealth.service;

import com.itconsulting.mentalHealth.core.entity.Role;
import com.itconsulting.mentalHealth.core.repository.RoleRepository;
import com.itconsulting.mentalHealth.core.service.RoleService;
import com.itconsulting.mentalHealth.resource.RoleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role createRole(RoleResource resource) {
        Role role = new Role();
        role.setName(resource.getName());
        return roleRepository.save(role);
    }


}