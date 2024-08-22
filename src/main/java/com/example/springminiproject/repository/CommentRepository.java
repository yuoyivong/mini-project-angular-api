package com.example.springminiproject.repository;

import com.example.springminiproject.model.Comment;
import com.example.springminiproject.response.dto.CommentDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findCommentByCommentIdAndArticle_ArticleId(Long cmtId, Long artId);

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE comment
        SET cmt = :comment,
            updated_at = CURRENT_TIMESTAMP
        WHERE comment_id = :cmtId AND article_id = :articleId AND user_id = :userId
    """, nativeQuery = true)
    void updateComment(Long cmtId, Long articleId, Long userId, String comment);

}
