package com.example.product_service.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lesson")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    @Column(name = "video_url", columnDefinition = "TEXT")
    private String videoUrl;
    @Column(name = "document_url", columnDefinition = "TEXT")
    private String documentUrl;
    @Column(name = "result_url")
    private String resultUrl;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}
