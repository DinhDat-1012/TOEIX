// ✅ ToeicTestDTO.java
package com.example.product_service.DTO;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ToeicTestDTO {
    private Long id;
    private String title;
    private String description;
    private List<QuestionDTO> questions;
}
