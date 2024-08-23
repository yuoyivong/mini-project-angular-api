package com.example.springminiproject.repository;

import com.example.springminiproject.model.Article;
import com.example.springminiproject.model.Bookmark;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = """
        UPDATE bookmark
        SET status = 0,
            updated_at = CURRENT_TIMESTAMP
        WHERE article_id = :articleId AND user_id = :userId
    """, nativeQuery = true)
    void updateBookmarkStatus(Long articleId, Long userId);

    @Query(value = """
        SELECT a
        FROM article a
        JOIN bookmark b ON a.articleId = b.article.articleId
        JOIN b.user u
        WHERE b.status = 1 AND u.userId = :userId
    """)
    Page<Article> getAllBookmarkArticles(Long userId, Pageable pageable);

}
