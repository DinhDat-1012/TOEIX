package com.example.auth_service.Controller;


import com.example.auth_service.Service.AuthService;
import com.example.auth_service.Service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

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
    @Autowired
    private EmailService emailService;

    @CrossOrigin(origins = "*")
    @GetMapping("/send-otp")
    public ResponseEntity<Map<String, String>> sendOtp(@RequestParam String email) {
        int otp = new Random().nextInt(900000) + 100000;

        String body = """
        Xin chào %s,

        Mã OTP để xác thực hành động của bạn là: %s
        Mã có hiệu lực trong 2 phút. Vui lòng không chia sẻ mã này cho bất kỳ ai.
        Đây là email được gửi tự động, vui lòng không trả lời email này (noreply).

        Trân trọng,
        Hệ thống xác thực ToeixLab
        """.formatted(email,otp);

        emailService.sendEmail(
                email,
                "Mã OTP xác thực - ToeixLab",
                body
        );
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"
                        ,"Access-Control-Allow-Headers", "Content-Type, Authorization"
                        ,"Access-Control-Allow-Credentials", "true") // Cho phép mọi domain
                .body(Map.of("status", "success"));
    }
}
