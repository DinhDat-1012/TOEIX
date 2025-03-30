package com.example.users_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class NotificationCreatedResponse {
    private Long id;
    private String title;
    private String message;
    private Boolean isRead;
    private LocalDateTime createdAt;
}
