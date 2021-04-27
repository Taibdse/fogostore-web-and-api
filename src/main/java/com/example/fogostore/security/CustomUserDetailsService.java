package com.example.fogostore.security;

import com.example.fogostore.common.enumeration.RoleName;
import com.example.fogostore.model.Role;
import com.example.fogostore.model.User;
import com.example.fogostore.repository.RoleRepository;
import com.example.fogostore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameEquals(username);
        if (user == null){
            throw new UsernameNotFoundException("Username not found");
        }
        Role role = roleRepository.findById(user.getRoleId()).orElse(null);
        return UserPrincipal.create(user, role.getName());
    }

    @Transactional
    public UserDetails loadUserById(int id) throws UsernameNotFoundException {
        User user = userRepository.findById(id).orElse(null);
        if (user == null){
            throw new UsernameNotFoundException("Username not found");
        }
        Role role = roleRepository.findById(user.getRoleId()).orElse(null);
        return UserPrincipal.create(user, role.getName());
    }
}