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
    public CommentDTO getCommentOnArticleById(Long id, Long userId) {
//        return commentRepository.findById(id).map(Comment::commentDTOResponse).orElseThrow();
        checkCommentOnArticle(id, userId);

        try {
            return commentRepository.findById(id)
                    .map(Comment::commentDTOResponse).orElseThrow();
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }

    }

    @Override
    public void deleteCommentById(Long id, Long userId) {
        checkCommentOnArticle(id, userId);

        try {
            commentRepository.deleteById(id);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public void updateCommentByCommentId(Long id, Long userId, CommentRequest commentRequest) {
        checkCommentOnArticle(id, userId);

        try {
            if(!commentRequest.getComment().isBlank()) {
                commentRepository.updateComment(id, userId, commentRequest.getComment());
            }
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

//    private void checkCommentOnArticle(Long cmtId) {
//        if(commentRepository.findById(cmtId).isEmpty()) {
//            throw new NotFoundException("Comment on article not found.");
//        }
//
//
//    }

    private void checkCommentOnArticle(Long cmtId, Long userId) {
        if(commentRepository.findCommentByCommentIdAndUser_UserId(cmtId, userId).isEmpty()) {
            throw new NotFoundException("Comment id " + cmtId + " not found.");
        }
    }


}
