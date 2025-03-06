package com.example.auth_service.Service;


import com.example.auth_service.DTO.UserIdentityResponseDTO;
import com.example.auth_service.DTO.UserRequestDTO;
import com.example.auth_service.DTO.UserResponseDTO;
import com.example.auth_service.Entity.User;
import com.example.auth_service.Repository.UserRepository;
import com.example.auth_service.Sercurity.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        log.info("A request is query:" + userOpt.toString());
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return jwtUtil.generateToken(username,userOpt.get().getEmail(), userOpt.get().getRoles());
        }
        throw new RuntimeException("Invalid credentials");
    }

    public boolean validateUserName2Token(String token, String username) {
       if(jwtUtil.extractUsername(token).equals(username)) {
           return true;
       }else{
           return false;
       }
    }

    public UserIdentityResponseDTO tokenCheckValidAndExtractI4(String token) {
        UserIdentityResponseDTO userIdentityResDTO = new UserIdentityResponseDTO();
       if(jwtUtil.isTokenValid(token)){
            userIdentityResDTO.setStatus("valid");
            userIdentityResDTO.setUsername(jwtUtil.extractUsername(token));
            userIdentityResDTO.setEmail(jwtUtil.extractEmail(token));
            userIdentityResDTO.setRole(jwtUtil.extractRole(token));
            return userIdentityResDTO;
       }else{
           userIdentityResDTO.setStatus("valid");
           userIdentityResDTO.setUsername("null");
           userIdentityResDTO.setEmail("null");
           userIdentityResDTO.setRole("null");
           return userIdentityResDTO;
       }

    }
    public UserResponseDTO registerUser(UserRequestDTO request) {
        UserResponseDTO userResDTO = new UserResponseDTO(request.getUsername(),request.getEmail());
        Optional<User> UserNameOpt = userRepository.findByUsername(request.getUsername());
        Optional<User> UserEmailOpt = userRepository.findByEmail(request.getEmail());
        if (UserNameOpt.isEmpty() && UserEmailOpt.isEmpty()) {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEmail(request.getEmail());
            user.setRoles(request.getRoles());
            userRepository.save(user);
        }else{
            throw new RuntimeException("User name already exists & email already exists");
        }
        log.info("Username : " + request.getUsername() + " Email : " + request.getEmail() + "was added to database");

        return userResDTO;
    }

}

