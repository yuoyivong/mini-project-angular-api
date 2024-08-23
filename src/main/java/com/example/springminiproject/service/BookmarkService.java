package com.example.springminiproject.service;

import com.example.springminiproject.response.dto.ArticleDTO;

import java.util.List;

public interface BookmarkService {

    void addBookmarkOnArticle(Long articleId, Long userId);

    void unBookmarkArticle(Long articleId, Long userId);

    List<ArticleDTO> getAllBookmarkArticlesByUserId(Long userId, int pageNo, int pageSize, String sortBy, String sortDirection);

}
