package com.example.fogostore.service;

import com.example.fogostore.common.enumeration.RoleName;
import com.example.fogostore.dto.auth.LoginRequestDto;
import com.example.fogostore.dto.auth.LoginResponseDto;
import com.example.fogostore.model.Role;
import com.example.fogostore.model.User;
import com.example.fogostore.repository.RoleRepository;
import com.example.fogostore.repository.UserRepository;
import com.example.fogostore.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public interface UserService {
    LoginResponseDto login(LoginRequestDto dto);
//    ErrorsDto<String> changePassword(ChangePasswordRequestDto dto);
    void signUpAdmin();
}

@Service
class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    private Map<String, String> checkPassword(String password){
        Map<String, String> errors = new HashMap<>();

        if(StringUtils.isEmpty(password)){
            errors.put("password", "Password is required!");
        } else {
            password = password.trim();
            if(password.length() < 8) errors.put("password", "Password's min length is 8 characters!");
            else if(password.matches("^(.*)\\s+(.*)$")) errors.put("password", "Password must not contain whitespace character");
            else if(!password.matches("^(.*)\\d+(.*)$")) errors.put("password", "Password must contain digit number!");
            else if(!password.matches("^(.*)[a-zA-Z]+(.*)$")) errors.put("password", "Password must contain alphabet character");
        }
        return errors;

    }

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return new LoginResponseDto(jwt, authentication.getAuthorities().toArray()[0].toString());
    }

//    @Override
//    public ErrorsDto<String> changePassword(ChangePasswordRequestDto dto) {
//        ErrorsDto<String> errorsDto;
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        dto.getUsername(),
//                        dto.getPassword()
//                )
//        );
//
//        if(authentication.isAuthenticated()){
//            User found = userRepository.findByUsername(dto.getUsername());
//            errorsDto = checkPassword(dto.getNewPassword());
//            if(errorsDto.isEmpty()){
//                found.setPassword(passwordEncoder.encode(dto.getNewPassword()));
//                userRepository.updateUser(found);
//            }
//        } else {
//            errorsDto = new ErrorsDto<>();
//            errorsDto.addErrors("UNAUTHORIZED", "Uaername and password do not match!");
//        }
//        return errorsDto;
//    }

    @Override
    public void signUpAdmin() {
        User found = userRepository.findByUsernameEquals("admin");
        if (found == null){
            System.out.println("create new admin");
            User user = new User();
            user.setName("admin");
            user.setPassword(passwordEncoder.encode("FogostoreAdmin@123"));
            user.setUsername("admin");
            Role role = roleRepository.findByNameEquals(RoleName.ADMIN);
            if(role == null){
                role = new Role();
                role.setName(RoleName.ADMIN);
                role = roleRepository.save(role);
            }
            user.setRoleId(role.getId());
            userRepository.save(user);
        }
    }

}
