package com.example.users_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AccCreatedAuthServAsyncDTO {
    private String username;
    private String password;
    private String email;
    private String roles;
}
