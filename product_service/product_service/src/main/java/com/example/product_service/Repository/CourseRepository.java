package com.example.product_service.Repository;

import com.example.product_service.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseCode(String courseCode);
    Optional<Course> findCoursesByCourseName(String courseName);
    List<Course> findByCourseNameContaining(String keyword);
    List<Course> findByPriceBetween(Double min, Double max);
    List<Course> findByCourseNameContainingIgnoreCase(String keyword);
    List<Course> findByCreatedAt(LocalDateTime createdAt);
    List<Course> findByUpdatedAt(LocalDateTime updatedAt);
    @Query(value = "SELECT c.* FROM course c " +
            "JOIN review r ON c.id = r.course_id " +
            "GROUP BY c.id " +
            "ORDER BY AVG(r.rating) DESC " +
            "LIMIT 1",
            nativeQuery = true)
    Course findTopRatedCourse();


}
