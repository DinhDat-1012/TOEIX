package com.example.auth_service.Controller;

import com.example.auth_service.DTO.UserRequestDTO;
import com.example.auth_service.DTO.UserResponseDTO;
import com.example.auth_service.Service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Console;

@Slf4j
@RestController
@RequestMapping("/auth-api")
public class AuthControllerApi {
    @Autowired
    private AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO>  register(@RequestBody UserRequestDTO UserRegisterRequest) {
        log.info("Registering user:" + UserRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Type", "Register")
                .body(authService.registerUser(UserRegisterRequest));
    }
}
