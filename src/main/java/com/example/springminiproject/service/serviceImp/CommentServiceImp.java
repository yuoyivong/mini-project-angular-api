package com.example.springminiproject.service.serviceImp;

import com.example.springminiproject.exception.NotFoundException;
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
        checkCommentOnArticle(id, articleId);

        try {
            return commentRepository.findCommentByCommentIdAndArticle_ArticleId(id, articleId)
                    .map(Comment::commentDTOResponse).orElseThrow();
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }

    }

    @Override
    public void deleteCommentById(Long id, Long articleId, Long userId) {
        checkCommentOnArticle(id, articleId);

        try {
            commentRepository.deleteById(id);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public void updateCommentByCommentId(Long id, Long articleId, Long userId, CommentRequest commentRequest) {
        checkCommentOnArticle(id, articleId);

        try {
            if(!commentRequest.getComment().isBlank()) {
                commentRepository.updateComment(id, articleId, userId, commentRequest.getComment());
            }
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    private void checkCommentOnArticle(Long cmtId, Long articleId) {
        if(commentRepository.findCommentByCommentIdAndArticle_ArticleId(cmtId, articleId).isEmpty()) {
            throw new NotFoundException("Comment on article not found.");
        }
    }

}
