package com.example.product_service.Service;

import com.example.product_service.DTO.QuestionDTO;
import com.example.product_service.Entity.Question;
import com.example.product_service.Repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    // Lấy ngẫu nhiên 1 câu hỏi
    public Question getRandomQuestion() {
        List<Question> allQuestions = questionRepository.findAll();
        if (allQuestions.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(allQuestions.size());
        return allQuestions.get(randomIndex);
    }

    public List<QuestionDTO> findQuestionByToeicTestIdAndPart(Long toeicTestId, Integer part) {
        List<Question> questions = questionRepository.findQuestionByToeicTestIdAndPart(toeicTestId, part);

        if (questions.isEmpty()) {
            return Collections.emptyList(); // Trả về list rỗng thay vì null
        }

        return questions.stream()
                .map(q -> {
                    QuestionDTO dto = new QuestionDTO();
                    dto.setId(q.getId());
                    dto.setContent(q.getContent());
                    dto.setOptionA(q.getOptionA());
                    dto.setOptionB(q.getOptionB());
                    dto.setOptionC(q.getOptionC());
                    dto.setOptionD(q.getOptionD());
                    dto.setCorrectAnswer(q.getCorrectAnswer());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
