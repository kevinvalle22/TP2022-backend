package com.itconsulting.mentalHealth.controller;

import com.itconsulting.mentalHealth.core.entity.DAOUser;
import com.itconsulting.mentalHealth.core.repository.UserRepository;
import com.itconsulting.mentalHealth.resource.AuthenticationRequest;
import com.itconsulting.mentalHealth.resource.AuthenticationResponse;
import com.itconsulting.mentalHealth.resource.UserResource;
import com.itconsulting.mentalHealth.security.JwtUtil;
import com.itconsulting.mentalHealth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, Pageable pageable)
            throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUserName(), authenticationRequest.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        }
        catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        Page<DAOUser> users = userRepository.findAll(pageable);
        // convert to list
        List<DAOUser> userList = users.getContent();
        for (DAOUser user : userList) {
            if (user.getUserName().equals(authenticationRequest.getUserName())) {
                authenticationRequest.setId(user.getId());
            }
        }
        UserDetails userdetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
        System.out.println(authenticationRequest.getUserName());
        String token = jwtUtil.generateToken(userdetails);
        return ResponseEntity.ok(new AuthenticationResponse(token,authenticationRequest.getId()));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> saveUser(@RequestBody UserResource user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }
}
