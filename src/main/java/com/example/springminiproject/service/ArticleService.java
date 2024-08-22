package com.example.springminiproject.service;

import com.example.springminiproject.request.ArticleRequest;
import com.example.springminiproject.request.CommentRequest;
import com.example.springminiproject.response.dto.ArticleDTO;

import java.util.List;

public interface ArticleService {
    ArticleDTO insertNewArticle(ArticleRequest articleRequest);

    List<ArticleDTO> getAllArticles(int pageNo, int pageSize, String sortBy, String sortDirection);

    ArticleDTO getArticleByArticleId(Long id) throws Exception;

    void deleteArticleByArticleId(Long id);

    void updateArticleByArticleId(Long id, ArticleRequest articleRequest);

    void postCommentByArticleId(CommentRequest commentRequest, Long id, Long userId);

//    List<UserCommentDTO> getCommentsByArticleId(Long id);

    void updatePostedComment(Long cmtId, Long articleId, Long userId, String cmt);

}
