package com.example.product_service.Service;

import com.example.product_service.DTO.CourseInformationResponse;
import com.example.product_service.DTO.FreeCourseResponse;
import com.example.product_service.Entity.Course;
import com.example.product_service.Repository.CourseRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {
    private final CourseRepository course_repository;

    public Optional<Course> findCourseByCourseCode(String courseCode) {
        return  course_repository.findByCourseCode(courseCode);
    }

    public List<CourseInformationResponse> findAllCoursesInformation() {
        return course_repository.findAll()
                .stream()
                .map(course -> CourseInformationResponse.builder()
                        .id(course.getId())
                        .courseCode(course.getCourseCode())
                        .courseName(course.getCourseName())
                        .description(course.getDescription())
                        .price(course.getPrice())
                        .courseUrl(course.getCourseUrl())
                        .imageUrl(course.getCourseImgUrl())
                        .build())
                .collect(Collectors.toList());
    }
    public List<Course> searchCourse(String keyword) {
        return course_repository.findByCourseNameContainingIgnoreCase(keyword);
    }
    public List<FreeCourseResponse> findFreeCourses() {
        return course_repository.findByPriceEquals((double)0).stream().map(
                course -> FreeCourseResponse.builder()
                        .courseUrl(course.getCourseUrl())
                        .courseCode(course.getCourseCode())
                        .courseName(course.getCourseName())
                        .description(course.getDescription())
                        .imageUrl(course.getCourseImgUrl())
                        .build()
        ).collect(Collectors.toList());
    }
}
