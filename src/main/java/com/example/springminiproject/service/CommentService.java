package com.example.springminiproject.service;

import com.example.springminiproject.request.CommentRequest;
import com.example.springminiproject.response.dto.CommentDTO;

public interface CommentService {

    CommentDTO getCommentByCommentId(Long id);

    void deleteCommentById(Long id);

    void updateCommentByCommentId(Long id, Long articleId, Long userId, CommentRequest commentRequest);

}
