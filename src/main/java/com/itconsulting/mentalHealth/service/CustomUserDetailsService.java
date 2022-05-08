package com.itconsulting.mentalHealth.service;

import com.itconsulting.mentalHealth.core.entity.DAOUser;
import com.itconsulting.mentalHealth.core.entity.Role;
import com.itconsulting.mentalHealth.core.repository.RoleRepository;
import com.itconsulting.mentalHealth.core.repository.UserRepository;
import com.itconsulting.mentalHealth.exception.ResourceNotFoundException;
import com.itconsulting.mentalHealth.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {





        DAOUser daoUser = userRepository.findByUserName(username);

        if (daoUser != null) {
            daoUser.setEnabled(true);
            return new
                    org.springframework.security.core.userdetails.User(
                    daoUser.getUserName(),
                    daoUser.getPassword(),
                    daoUser.getEnabled(),
                    true,
                    true,
                    true,
                    getAuthorities(daoUser));

        }
        throw new UsernameNotFoundException("User not found with the name " + username);    }

    private Collection<? extends GrantedAuthority> getAuthorities(DAOUser user) {

        List<GrantedAuthority> authorities = new ArrayList<>();
        System.out.println(authorities);
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        System.out.println(authorities);
        return authorities;
    }

    public DAOUser save(UserResource user) {
        DAOUser newDAOUser = new DAOUser();

        Role role = roleRepository.findByName("ROLE_USER");
        newDAOUser.setUserName(user.getUserName());
        newDAOUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newDAOUser.setRole(role);

        newDAOUser.setUniversity(user.getUniversity());

        newDAOUser.setEmail(user.getEmail());

        newDAOUser.setPhone(user.getPhone());
        newDAOUser.setProvince(user.getProvince());
        newDAOUser.setDistrict(user.getDistrict());


        newDAOUser.setEnabled(false);
        System.out.println(newDAOUser.getRole());

        return userRepository.save(newDAOUser);
    }
    public Page<DAOUser> getAllUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public DAOUser getUserById(Long userId) {

        DAOUser user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return user;
    }


    public DAOUser getUserByEmail(String email) {
        return userRepository.findDAOUserByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Email not found","email",email));
    }
    public DAOUser updateUser(Long userId, DAOUser user){
        DAOUser newDAOUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Role role = roleRepository.findByName("ROLE_USER");
        newDAOUser.setUserName(user.getUserName());
        newDAOUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newDAOUser.setRole(role);

        newDAOUser.setUniversity(user.getUniversity());

        newDAOUser.setEmail(user.getEmail());

        newDAOUser.setPhone(user.getPhone());
        newDAOUser.setProvince(user.getProvince());
        newDAOUser.setDistrict(user.getDistrict());

        return userRepository.save(newDAOUser);

    }

    public ResponseEntity<?> deleteUser(Long userId) {
        DAOUser user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

}