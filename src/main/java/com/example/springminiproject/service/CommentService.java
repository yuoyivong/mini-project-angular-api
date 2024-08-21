package com.example.springminiproject.service;

import com.example.springminiproject.request.CommentRequest;
import com.example.springminiproject.response.dto.CommentDTO;

public interface CommentService {

    CommentDTO getCommentOnArticle(Long id, Long articleId);

    void deleteCommentById(Long id, Long articleId, Long userId);

    void updateCommentByCommentId(Long id, Long articleId, Long userId, CommentRequest commentRequest);

}
