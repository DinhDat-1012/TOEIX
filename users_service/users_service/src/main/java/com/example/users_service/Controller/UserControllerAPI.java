package com.example.users_service.Controller;

import com.example.users_service.DTO.AccCreatedAuthServAsyncDTO;
import com.example.users_service.DTO.UserRequestDTO;
import com.example.users_service.DTO.UserResponseDTO;
import com.example.users_service.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserControllerAPI {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO request) {
        UserResponseDTO userResponseDTO = userService.registerUser(request);
        if(userResponseDTO!=null) {
            AccCreatedAuthServAsyncDTO accCreatedAuthServAsyncDTO = new AccCreatedAuthServAsyncDTO(request.getUsername(),request.getPassword(), request.getEmail(), request.getRole());
            userService.UserAccAsync2AuthServ(accCreatedAuthServAsyncDTO).subscribe();
            return ResponseEntity.ok(userResponseDTO);
        }throw(new RuntimeException("Username already exists"));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
}