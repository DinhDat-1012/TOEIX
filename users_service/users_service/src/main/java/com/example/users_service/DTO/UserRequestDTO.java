package com.example.users_service.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequestDTO {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private Boolean gender;
    private LocalDate birthday;
    private String address;
    private String role;
}