// ✅ QuestionDTO.java
package com.example.product_service.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionDTO {
    private Long id;
    private String imageUrl;
    private String content;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;
}
