package com.example.product_service.Service;

import com.example.product_service.Entity.Vocabulary;
import com.example.product_service.Repository.VocabularyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VocabularyService {

    @Autowired
    private VocabularyRepository vocabularyRepository;

    // Lấy danh sách từ vựng ôn tập (mặc định lấy 20 từ)
    public List<Vocabulary> getReviewList() {
        return vocabularyRepository.findRandomVocabulary(20);
    }

    // Hàm thêm từ mới (để test data)
    public Vocabulary saveVocabulary(Vocabulary vocab) {
        return vocabularyRepository.save(vocab);
    }
}
