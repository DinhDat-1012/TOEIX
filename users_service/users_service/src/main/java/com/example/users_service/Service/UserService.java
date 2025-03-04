package com.example.users_service.Service;

import com.example.users_service.DTO.AccCreatedAuthServAsyncDTO;
import com.example.users_service.DTO.UserRequestDTO;
import com.example.users_service.DTO.UserResponseDTO;
import com.example.users_service.Entity.Role;
import com.example.users_service.Entity.User;
import com.example.users_service.Repository.RoleRepository;
import com.example.users_service.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserService{
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  RoleRepository roleRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    public UserService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }


    public UserResponseDTO registerUser(UserRequestDTO request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Role role = roleRepository.findByName(request.getRole().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setGender(request.getGender());
        user.setBirthday(request.getBirthday());
        user.setAddress(request.getAddress());
        user.setRole(role);

        userRepository.save(user);
        return new UserResponseDTO(user.getUsername(), user.getEmail(), user.getFullName(), role.getName());
    }

    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponseDTO(user.getUsername(), user.getEmail(), user.getFullName(), user.getRole().getName());
    }

    public Mono<String> UserAccAsync2AuthServ(AccCreatedAuthServAsyncDTO AccCreatedAuthServAsyncDTO) {
        String url = "http://localhost:8000/auth-api/register"; // API AuthService

        // Tạo request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username",AccCreatedAuthServAsyncDTO.getUsername());
        requestBody.put("password",AccCreatedAuthServAsyncDTO.getPassword());
        requestBody.put("email",AccCreatedAuthServAsyncDTO.getEmail());
        requestBody.put("roles",AccCreatedAuthServAsyncDTO.getRoles());

        return webClientBuilder.build()
                .post()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(Response -> log.info("Response:"+ Response))
                .doOnError(Error-> log.error("Error:"+ Error)); // Trả về dữ liệu từ AuthService
    }

}