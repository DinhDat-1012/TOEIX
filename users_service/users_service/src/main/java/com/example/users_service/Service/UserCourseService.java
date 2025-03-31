package com.example.users_service.Service;

import com.example.users_service.DTO.AuthServiceJWTCheckResponse;
import com.example.users_service.DTO.UserCourseResponseDTO;
import com.example.users_service.Entity.PurchasedCourse;
import com.example.users_service.Repository.UserCourseRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.List;

@Service
public class UserCourseService {
    @Autowired
    UserCourseRepository userCourseRepository;
    @Autowired
    UserService userService;

    public List<UserCourseResponseDTO> findUserCourseByUserName(String token,String userName) {
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

        List<PurchasedCourse> userCourses = userCourseRepository.findByUserId(userService.getUserIdByUsername(userName));
        if (userCourses == null) {
            return new ArrayList<>();
        }
        List<UserCourseResponseDTO> userCourseResponseDTOS = new ArrayList<>();
        for (PurchasedCourse userCourse : userCourses) {
            UserCourseResponseDTO responseDTO = new UserCourseResponseDTO();
            responseDTO.setCourseCode(userCourse.getCourseCode());
            responseDTO.setCourse_names(userCourse.getCourse_names());
            responseDTO.setPurchaseDate(userCourse.getPurchaseDate());
            responseDTO.setPrice(userCourse.getPrice());
            userCourseResponseDTOS.add(responseDTO);
        }
        return userCourseResponseDTOS;
    }

}
