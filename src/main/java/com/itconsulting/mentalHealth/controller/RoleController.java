package com.itconsulting.mentalHealth.controller;

import com.itconsulting.mentalHealth.core.entity.Role;
import com.itconsulting.mentalHealth.core.service.RoleService;
import com.itconsulting.mentalHealth.resource.RoleResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class RoleController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RoleService roleService;
    @PostMapping("/roles")
    public RoleResource createARole(@Valid @RequestBody RoleResource resource) {
        return convertToResource(roleService.createRole(resource));

    }


    private Role convertToEntity(RoleResource resource) {
        return mapper.map(resource, Role.class);
    }

    private RoleResource convertToResource(Role entity) {
        return mapper.map(entity, RoleResource.class);
    }
}
