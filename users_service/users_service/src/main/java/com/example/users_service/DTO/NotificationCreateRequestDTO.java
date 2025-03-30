package com.example.users_service.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class NotificationCreateRequestDTO {
    private String token;  // JWT Token để xác thực
    private Long userId;
    private String title;
    private String message;
}
