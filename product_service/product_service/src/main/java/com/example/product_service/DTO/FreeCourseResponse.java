package com.example.product_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreeCourseResponse {
    private String courseName;
    private String courseCode;
    private String description;
    private String imageUrl;
    private String courseUrl;
}
