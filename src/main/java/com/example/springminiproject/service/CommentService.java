package com.example.springminiproject.service;

import com.example.springminiproject.request.CommentRequest;
import com.example.springminiproject.response.dto.CommentDTO;

public interface CommentService {

    CommentDTO getCommentOnArticleById(Long id, Long userId);

    void deleteCommentById(Long id,  Long userId);

    void updateCommentByCommentId(Long id, Long userId, CommentRequest commentRequest);

}
