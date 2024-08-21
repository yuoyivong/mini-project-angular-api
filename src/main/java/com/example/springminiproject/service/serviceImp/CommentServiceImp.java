package com.example.springminiproject.service.serviceImp;

import com.example.springminiproject.model.Comment;
import com.example.springminiproject.repository.CommentRepository;
import com.example.springminiproject.request.CommentRequest;
import com.example.springminiproject.response.dto.CommentDTO;
import com.example.springminiproject.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImp implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImp(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDTO getCommentOnArticle(Long id, Long articleId) {
//        return commentRepository.findById(id).map(Comment::commentDTOResponse).orElseThrow();
        return commentRepository.findCommentByCommentIdAndArticle_ArticleId(id, articleId).commentDTOResponse();
    }

    @Override
    public void deleteCommentById(Long id, Long articleId, Long userId) {
        commentRepository.deleteById(id);
    }

    @Override
    public void updateCommentByCommentId(Long id, Long articleId, Long userId, CommentRequest commentRequest) {
        commentRepository.updateComment(id, articleId, userId, commentRequest.getComment());
    }
}
