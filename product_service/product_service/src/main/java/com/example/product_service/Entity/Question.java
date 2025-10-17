package com.example.product_service.Entity;

import com.example.product_service.Entity.ToeicTest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Question.java
@Entity
@Table(name = "toeic_question")
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toeic_test_id")
    private ToeicTest toeicTest;

    private Integer part;
    @Column(columnDefinition = "TEXT")
    private String content;

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    private String audioUrl;
    private String imageUrl;

    // getter, setter
}
