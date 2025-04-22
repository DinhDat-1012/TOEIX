package com.example.users_service.Controller;

import com.example.users_service.DTO.AuthServiceJWTCheckResponse;
import com.example.users_service.DTO.PersonalPageRequest;
import com.example.users_service.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/staff")
    public String showLandingPage(Model model) {
        return "landing_page"; // Trả về tên file Thymeleaf trong templates
    }

    @GetMapping("/personal")
    public String showPersonalPage(Model model) {
        return "personal_page"; // Trả về tên file Thymeleaf trong templates
    }

    @GetMapping("/review")
    public String showReviewPage(Model model) {
        return "review_page"; // Trả về tên file Thymeleaf trong templates
    }
    @GetMapping("/test-personal")
    public String showPersonalPage(@RequestHeader String username,@RequestHeader String token ,Model model) {

        // Chặn luồng chính đợi response từ userService
        String response = userService.CheckValidToken(username, token).block();

        // Chuyển đổi JSON thành đối tượng AuthServiceJWTCheckResponse
        ObjectMapper objectMapper = new ObjectMapper();
        AuthServiceJWTCheckResponse authResponse;
        try {
            authResponse = objectMapper.readValue(response, AuthServiceJWTCheckResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Lỗi khi parse JSON", e);
        }
        if(authResponse.getStatus().equals("valid")) {
            return "personal_page";
        }throw (new RuntimeException("Invalid Token"));

    }
    @GetMapping(path = "/home")
    public String showHomePage(Model model) {
        return "home_page";
    }

}
