package com.example.users_service.Service;

import com.example.users_service.DTO.AuthServiceJWTCheckResponse;
import com.example.users_service.DTO.NotificationCreateRequestDTO;
import com.example.users_service.DTO.NotificationCreatedResponse;
import com.example.users_service.Entity.Notification;
import com.example.users_service.Entity.User;
import com.example.users_service.Repository.NotificationRepository;
import com.example.users_service.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    // Tạo thông báo mới
    public NotificationCreatedResponse createNotification(NotificationCreateRequestDTO notificationRequestDto) {
        User user = userRepository.findById(notificationRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(notificationRequestDto.getTitle());
        notification.setMessage(notificationRequestDto.getMessage());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
        return convertToDTO(notification);
    }

    //Chuyển từ Entity -> DTO
    private NotificationCreatedResponse convertToDTO(Notification notification) {
        return new NotificationCreatedResponse(
                notification.getId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getIsRead(),
                notification.getCreatedAt()
        );
    }
    public List<NotificationCreatedResponse> getNotifications(String token, String userName) {
        // Gọi service kiểm tra token
        String response = userService.CheckValidToken(userName, token).block();

        // Parse JSON response từ auth service
        ObjectMapper objectMapper = new ObjectMapper();
        AuthServiceJWTCheckResponse authResponse;
        try {
            authResponse = objectMapper.readValue(response, AuthServiceJWTCheckResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Lỗi khi parse JSON từ AuthService", e);
        }

        // Kiểm tra nếu token hợp lệ
        if (!"valid".equals(authResponse.getStatus())) {
            throw new RuntimeException("Invalid token");
        }

        // Lấy danh sách thông báo chưa đọc
        return userRepository.findByUsername(userName)
                .map(user -> notificationRepository.findByUserIdAndIsReadFalse(user.getId())
                        .stream()
                        .map(this::convertToDTO) // Chuyển từ Entity -> DTO
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

}

