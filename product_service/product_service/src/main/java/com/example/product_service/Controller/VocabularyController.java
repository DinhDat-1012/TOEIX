package com.example.product_service.Controller;


import com.example.product_service.Entity.Vocabulary;
import com.example.product_service.Repository.VocabularyRepository;
import com.example.product_service.Service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
@CrossOrigin(origins = "*") // Cho phép Frontend gọi API không bị chặn CORS
public class VocabularyController {

    @Autowired
    private VocabularyService vocabularyService;

    // API lấy danh sách từ ôn tập
    // URL: http://localhost:8080/api/v1/review/vocabulary
    @GetMapping("/vocabulary")
    public ResponseEntity<List<Vocabulary>> getVocabularyForReview() {
        List<Vocabulary> list = vocabularyService.getReviewList();
        return ResponseEntity.ok(list);
    }

    // API tạo data mẫu (dùng Postman để thêm nhanh)
    @PostMapping("/add")
    public ResponseEntity<Vocabulary> addVocabulary(@RequestBody Vocabulary vocabulary) {
        return ResponseEntity.ok(vocabularyService.saveVocabulary(vocabulary));
    }
}