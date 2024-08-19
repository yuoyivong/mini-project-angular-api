package com.example.springminiproject.service.serviceImp;

import com.example.springminiproject.repository.ArticleRepository;
import com.example.springminiproject.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImp implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleServiceImp(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
}
