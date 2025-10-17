package com.example.product_service.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.aspectj.weaver.patterns.TypePatternQuestions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// ToeicTest.java
@Entity
@Table(name = "toeic_test")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToeicTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Integer durationMinutes;
    private String level;
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "toeicTest", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Question> questions = new ArrayList<>();

    // getter, setter
}
