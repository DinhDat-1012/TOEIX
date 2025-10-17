package com.example.product_service.Controller;

import com.example.product_service.DTO.*;
import com.example.product_service.Entity.Course;
import com.example.product_service.Entity.Question;
import com.example.product_service.Service.CourseService;
import com.example.product_service.Service.ToeicTestService;
import com.example.product_service.Service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/product/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ToeicTestService toeicTestService;
    private final QuestionService questionService;

    @Autowired
    private final CourseService courseService;

    // ================== COURSE API ==================

    @GetMapping("/all-course")
    public ResponseEntity<List<CourseInformationResponse>> getAllCourse() {
        try {
            return ResponseEntity.ok()
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS",
                            "Access-Control-Allow-Headers", "Content-Type, Authorization",
                            "Access-Control-Allow-Credentials", "true")
                    .body(courseService.findAllCoursesInformation());
        } catch (Exception e) {
            log.error("Error when fetching all courses: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/courses/search")
    public ResponseEntity<List<CourseResponse>> searchCourses(@RequestParam String keyword) {
        List<Course> courses = courseService.searchCourse(keyword);
        List<CourseResponse> responses = courses.stream()
                .map(course -> new CourseResponse(course.getId(), course.getCourseName(),
                        course.getCourseCode(), course.getDescription()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/courses/free-courses")
    public ResponseEntity<List<FreeCourseResponse>> searchFreeCourses() {
        List<FreeCourseResponse> responses = courseService.findFreeCourses();
        return ResponseEntity.ok().body(responses);
    }

    // ================== TOEIC TEST API ==================

    // 🧩 Lấy toàn bộ câu hỏi của 1 đề thi TOEIC
    @GetMapping("/tests/{testId}/questions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByTestId(@PathVariable Long testId) {
        List<QuestionDTO> questions = toeicTestService.getQuestionsByTestId(testId);
        return ResponseEntity.ok(questions);
    }

    // 🧾 Lấy thông tin đề + danh sách câu hỏi
    @GetMapping("/tests/{testId}")
    public ResponseEntity<ToeicTestDTO> getTestWithQuestions(@PathVariable Long testId) {
        ToeicTestDTO test = toeicTestService.getTestWithQuestions(testId);
        return ResponseEntity.ok(test);
    }
    @GetMapping("/test/{toeicTestId}/part/{part}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByTestAndPart(
            @PathVariable Long toeicTestId,
            @PathVariable Integer part) {
        List<QuestionDTO> questions = questionService.findQuestionByToeicTestIdAndPart(toeicTestId, part);
        return ResponseEntity.ok(questions);
    }
}
