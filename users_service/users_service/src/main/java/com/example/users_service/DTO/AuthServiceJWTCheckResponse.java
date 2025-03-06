package com.example.users_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthServiceJWTCheckResponse {
    String status;
    String username;
    String email;
    String role;
}
