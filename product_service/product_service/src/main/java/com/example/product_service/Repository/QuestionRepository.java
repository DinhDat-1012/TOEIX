// ✅ QuestionRepository.java
package com.example.product_service.Repository;

import com.example.product_service.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByToeicTestId(Long testId);

    List<Question> findQuestionByToeicTestIdAndPart(Long toeicTestId, Integer part);

}
