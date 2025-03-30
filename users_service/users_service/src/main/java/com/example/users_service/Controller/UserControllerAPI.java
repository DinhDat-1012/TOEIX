package com.example.users_service.Controller;

import com.example.users_service.DTO.*;
import com.example.users_service.Service.NotificationService;
import com.example.users_service.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserControllerAPI {
    private final UserService userService;
    private final NotificationService notificationService;
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO request) {
        UserResponseDTO userResponseDTO = userService.registerUser(request);
        if(userResponseDTO!=null) {
            AccCreatedAuthServAsyncDTO accCreatedAuthServAsyncDTO = new AccCreatedAuthServAsyncDTO(request.getUsername(),request.getPassword(), request.getEmail(), request.getRole());
            userService.UserAccAsync2AuthServ(accCreatedAuthServAsyncDTO).subscribe();
            return ResponseEntity.ok(userResponseDTO);
        }throw(new RuntimeException("Username already exists"));
    }
    @PostMapping("/get-user-profile")
    public ResponseEntity<UserResponseDTO> getUser(@RequestHeader String token,@RequestHeader String username ) {
        String response = userService.CheckValidToken(username, token).block();
        ObjectMapper objectMapper = new ObjectMapper();
        AuthServiceJWTCheckResponse authResponse;
        try {
            authResponse = objectMapper.readValue(response, AuthServiceJWTCheckResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Lỗi khi parse JSON", e);
        }
        if(authResponse.getStatus().equals("valid")) {
            return ResponseEntity.ok(userService.getUserByUsername(username));
        }else{
            throw(new RuntimeException("Invalid token"));
        }

    }
    @PostMapping("/api/v1/notification")
    public ResponseEntity<List<NotificationCreatedResponse>> getNotification(@RequestHeader String token, @RequestHeader String username ) {
        List<NotificationCreatedResponse> notifications = notificationService.getNotifications(token,username);
        return ResponseEntity.ok(notifications);
    }
}