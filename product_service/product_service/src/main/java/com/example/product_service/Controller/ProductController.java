package com.example.product_service.Controller;

import com.example.product_service.DTO.CourseInformationResponse;
import com.example.product_service.DTO.CourseResponse;
import com.example.product_service.DTO.FreeCourseResponse;
import com.example.product_service.Entity.Course;
import com.example.product_service.Service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/courses/search")
    public ResponseEntity<List<CourseResponse>> searchCourses(@RequestParam String keyword) {
        List<Course> courses = course_service.searchCourse(keyword);
        List<CourseResponse> responses = courses.stream()
                .map(course -> new CourseResponse(course.getId(), course.getCourseName(), course.getCourseCode(),course.getDescription()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"
                ,"Access-Control-Allow-Headers", "Content-Type, Authorization"
                ,"Access-Control-Allow-Credentials", "true").body(responses);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/v1/courses/free-courses")
    public ResponseEntity<List<FreeCourseResponse>> searchFreeCourses() {
        List<FreeCourseResponse>responses = course_service.findFreeCourses();
        return ResponseEntity.ok().header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"
                ,"Access-Control-Allow-Headers", "Content-Type, Authorization"
                ,"Access-Control-Allow-Credentials", "true").body(responses);
    }
}
