package com.example.users_service.DTO;

import com.example.users_service.Entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCourseResponseDTO {
    private String courseCode;
    private String course_names;
    private LocalDateTime purchaseDate;
    private BigDecimal price;
}
