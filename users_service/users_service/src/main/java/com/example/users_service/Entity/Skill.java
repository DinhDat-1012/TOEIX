package com.example.users_service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "skill")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ielts_score", nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double ieltsScore = 0.0;

    @Column(name = "toeic_score", nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double toeicScore = 0.0;

    @Column(name = "reading_score", nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double readingScore = 0.0;

    @Column(name = "writing_score", nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double writingScore = 0.0;

    @Column(name = "listening_score", nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double listeningScore = 0.0;

    @Column(name = "speaking_score", nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double speakingScore = 0.0;

    @OneToOne
    @MapsId
    private User user;
}

