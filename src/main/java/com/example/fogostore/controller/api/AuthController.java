package com.example.fogostore.controller.api;

import com.example.fogostore.dto.auth.LoginRequestDto;
import com.example.fogostore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(userService.login(dto));
    }

    //    @PostMapping("/change-password")
//    public <Any> Any changePassword(@RequestBody ChangePasswordRequestDto dto){
//        ErrorsDto<String> errorsDto = userService.changePassword(dto);
//        if(errorsDto.isEmpty()) return (Any) AppUtils.getSuccessResponse();
//        return (Any) errorsDto;
//    }

//    @GetMapping("/test-authorization")
//    public String testAuthorization(){
//        return "";
//    }

}
