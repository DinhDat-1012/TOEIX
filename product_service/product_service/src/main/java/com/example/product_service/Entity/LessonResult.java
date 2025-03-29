package com.example.product_service.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "lesson_result")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "user_id")
    private Long userID; // Nếu bạn đã có entity User, nếu chưa có thì để Long userId

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "score")
    private Double score; // Nếu có chấm điểm

    @Column(name = "completed")
    private Boolean completed; // Đã hoàn thành hay chưa

    @Column(name = "time_spent")
    private Integer timeSpent; // Thời gian học (tính bằng phút)

    @Column(name = "attempted_at")
    private LocalDateTime attemptedAt;
}
