package com.example.springminiproject.repository;

import com.example.springminiproject.model.Article;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = """
        UPDATE article 
        SET title = :title,
            description = :description,
            updated_at = CURRENT_TIMESTAMP
        WHERE article_id = :id AND user_id = :userId
    """, nativeQuery = true)
    void updateArticleByArticleId(Long id, String title, String description, Long userId);

//    post comment via article id and user id
    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO comment(cmt, created_at, article_id, user_id)
        VALUES(:cmt, CURRENT_TIMESTAMP, :articleId, :userId)
    """, nativeQuery = true)
    void postCommentOnArticle(String cmt, Long articleId, Long userId);

    void deleteArticleByArticleIdAndUser_UserId(Long articleId, Long userId);

    Optional<Article> findArticleByArticleIdAndUser_UserId(Long articleId, Long userId);


}
