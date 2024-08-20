package com.example.springminiproject.repository;

import com.example.springminiproject.model.Article;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE article 
        SET title = :title,
            description = :description,
            updated_at = CURRENT_TIMESTAMP
        WHERE article_id = :id
    """, nativeQuery = true)
    void updateArticleByArticleId(Long id, String title, String description);

//    post comment via article id and user id
    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO comment(cmt, created_at, article_id, user_id)
        VALUES(:cmt, CURRENT_TIMESTAMP, :articleId, :userId)
    """, nativeQuery = true)
    void postCommentOnArticle(String cmt, Long articleId, Long userId);

    @Query(value = """
        SELECT *
        FROM article a INNER JOIN (comment c INNER JOIN user_tb u ON c.user_id = u.user_id) ON a.article_id = c.article_id;
    """, nativeQuery = true)
    Page<Article> getAll(Pageable pageable);

}
