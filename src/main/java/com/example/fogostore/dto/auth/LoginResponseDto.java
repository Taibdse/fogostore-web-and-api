package com.example.fogostore.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String jwtToken;
    private String role;

    public LoginResponseDto(String jwtToken, String role) {
        this.jwtToken = jwtToken;
        this.role = role;
    }
}
