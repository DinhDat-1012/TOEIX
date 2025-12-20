package com.example.product_service.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Vocabulary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String english;      // Từ tiếng Anh

    @Column(nullable = false)
    private String vietnamese;   // Nghĩa tiếng Việt

    private String type;         // Loại từ (verb, noun, adj...)

    @Column(columnDefinition = "TEXT")
    private String example;      // Ví dụ minh họa

    @Column(columnDefinition = "TEXT")
    private String image;        // Link ảnh minh họa

    @Column(columnDefinition = "TEXT")
    private String video;        // Link video minh họa
}
