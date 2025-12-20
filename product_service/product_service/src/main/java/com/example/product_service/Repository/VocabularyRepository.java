package com.example.product_service.Repository;

import com.example.product_service.Entity.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {

    // Lấy ngẫu nhiên 'limit' từ vựng để ôn tập
    // Lưu ý: RAND() dùng cho MySQL, nếu dùng PostgreSQL thì đổi thành RANDOM()
    @Query(value = "SELECT * FROM vocabulary ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Vocabulary> findRandomVocabulary(@Param("limit") int limit);
}