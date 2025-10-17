// ✅ ToeicTestService.java
package com.example.product_service.Service;

import com.example.product_service.DTO.QuestionDTO;
import com.example.product_service.DTO.ToeicTestDTO;
import com.example.product_service.Entity.ToeicTest;
import com.example.product_service.Repository.QuestionRepository;
import com.example.product_service.Repository.ToeicTestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToeicTestService {
    private final ToeicTestRepository testRepository;
    private final QuestionRepository questionRepository;

    public ToeicTestService(ToeicTestRepository testRepository, QuestionRepository questionRepository) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
    }

    public List<QuestionDTO> getQuestionsByTestId(Long testId) {
        return questionRepository.findByToeicTestId(testId)
                .stream()
                .map(q -> new QuestionDTO(
                        q.getId(),
                        q.getContent(),
                        q.getOptionA(),
                        q.getOptionB(),
                        q.getOptionC(),
                        q.getOptionD(),
                        q.getCorrectAnswer()
                ))
                .collect(Collectors.toList());
    }

    public ToeicTestDTO getTestWithQuestions(Long testId) {
        ToeicTest test = testRepository.findById(testId).orElseThrow();
        List<QuestionDTO> questions = getQuestionsByTestId(testId);
        return new ToeicTestDTO(test.getId(), test.getTitle(), test.getDescription(), questions);
    }
}
