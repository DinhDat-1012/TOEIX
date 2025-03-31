package com.example.users_service.Repository;

import com.example.users_service.Entity.PurchasedCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCourseRepository extends JpaRepository<PurchasedCourse,Long> {
    PurchasedCourse findByCourseCode(String courseCode);
    List<PurchasedCourse> findByUserId(Long id);
}
