package com.example.auth_service.Service;


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
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return jwtUtil.generateToken(username);
        }
        throw new RuntimeException("Invalid credentials");
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

