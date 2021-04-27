package com.example.fogostore.controller.api;

import com.example.fogostore.dto.auth.LoginRequestDto;
import com.example.fogostore.dto.auth.LoginResponseDto;
import com.example.fogostore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {

}
