package com.example.springminiproject.service;

import com.example.springminiproject.request.ArticleRequest;
import com.example.springminiproject.request.CommentRequest;
import com.example.springminiproject.response.dto.ArticleDTO;
import com.example.springminiproject.response.dto.UserDTO;

import java.util.List;

public interface ArticleService {
    ArticleDTO insertNewArticle(ArticleRequest articleRequest, UserDTO user) throws Exception;

    List<ArticleDTO> getAllArticles(int pageNo, int pageSize, String sortBy, String sortDirection);

    ArticleDTO getArticleByArticleId(Long id) throws Exception;

    void deleteArticleByArticleId(Long id, Long userId);

    void updateArticleByArticleId(Long id, ArticleRequest articleRequest, Long userId);

    void postCommentByArticleId(CommentRequest commentRequest, Long id, Long userId);

//    List<UserCommentDTO> getCommentsByArticleId(Long id);

//    void updatePostedComment(Long cmtId, Long userId, String cmt);

}
