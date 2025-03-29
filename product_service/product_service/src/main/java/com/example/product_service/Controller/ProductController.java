package com.example.product_service.Controller;

import com.example.product_service.DTO.CourseInformationResponse;
import com.example.product_service.Service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(path = "/product/api")
@RequiredArgsConstructor
public class ProductController {
@Autowired
private final  CourseService course_service;

@GetMapping(path = "/v1/all-course")
public ResponseEntity<List<CourseInformationResponse>> getAllCourse(){
    try{
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"
                        ,"Access-Control-Allow-Headers", "Content-Type, Authorization"
                        ,"Access-Control-Allow-Credentials", "true")
                .body(course_service.findAllCoursesInformation());
    }catch (Exception e){
        log.error(e.getMessage());
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
}
