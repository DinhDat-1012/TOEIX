package com.example.auth_service.Controller;


import com.example.auth_service.Service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        String token = authService.authenticate(request.get("username"), request.get("password"));
        log.info("Login token:" + token);
        // Tạo response với header CORS
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"
                        ,"Access-Control-Allow-Headers", "Content-Type, Authorization"
                        ,"Access-Control-Allow-Credentials", "true") // Cho phép mọi domain
                .body(Map.of("token", token));
    }
}
