package com.example.product_service.Repository;

import com.example.product_service.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Lấy tất cả review của 1 course
    List<Review> findByCourseId(Long courseId);

    // Tính rating trung bình của 1 course
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.course.id = :courseId")
    Double getAverageRatingByCourse(@Param("courseId") Long courseId);

    // Lấy review có rating cao nhất của course
    @Query("SELECT r FROM Review r WHERE r.course.id = :courseId ORDER BY r.rating DESC LIMIT 1")
    Optional<Review> findTopReviewByCourse(@Param("courseId") Long courseId);
}
