package com.example.product_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseInformationResponse {
    private long id;
    private String courseCode;
    private String courseName;
    private String description;
    private Double price;
    private String imageUrl;
    private String courseUrl;
}
