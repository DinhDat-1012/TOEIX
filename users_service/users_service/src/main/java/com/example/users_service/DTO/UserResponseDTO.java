package com.example.users_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private String username;
    private String email;
    private String fullName;
    private String role;
}

