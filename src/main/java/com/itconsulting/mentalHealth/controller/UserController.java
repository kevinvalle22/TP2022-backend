package com.itconsulting.mentalHealth.controller;

import com.itconsulting.mentalHealth.core.entity.DAOUser;
import com.itconsulting.mentalHealth.resource.UserResource;
import com.itconsulting.mentalHealth.service.CustomUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/users")
    public Page<UserResource> getAllUsers(Pageable pageable, Principal principal) {
        String name = principal.getName();
        System.out.println(name);
        Page<DAOUser> usersPage = userDetailsService.getAllUsers(pageable);
        List<UserResource> resources = usersPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }
    @GetMapping(value = "/users/{userId}")
    public UserResource getUserById(
            @PathVariable(name = "userId") Long userId) {
        return convertToResource(userDetailsService.getUserById(userId));
    }
    @PutMapping("/users/{userId}")
    public UserResource updateUser(@PathVariable(name = "userId") Long userId, @Valid @RequestBody UserResource resource) {
        DAOUser user = convertToEntity(resource);
        return convertToResource(userDetailsService.updateUser(userId, user));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "userId") Long userId) {
        return userDetailsService.deleteUser(userId);
    }
    private DAOUser convertToEntity(UserResource resource) {
        return mapper.map(resource, DAOUser.class);
    }

    private UserResource convertToResource(DAOUser entity) {
        return mapper.map(entity, UserResource.class);
    }
}
