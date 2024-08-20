package com.example.springminiproject.service.serviceImp;

import com.example.springminiproject.model.Article;
import com.example.springminiproject.repository.ArticleRepository;
import com.example.springminiproject.repository.CommentRepository;
import com.example.springminiproject.request.ArticleRequest;
import com.example.springminiproject.request.CommentRequest;
import com.example.springminiproject.response.dto.ArticleDTO;
import com.example.springminiproject.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImp implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public ArticleServiceImp(ArticleRepository articleRepository, CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public ArticleDTO insertNewArticle(ArticleRequest articleRequest) {

        Article article = new Article();
        article.setTitle(articleRequest.getTitle());
        article.setDescription(articleRequest.getDescription());
        article.setCreatedAt(LocalDateTime.now());

        return articleRepository.save(article).articleDTOResponse();

    }

    @Override
    public List<ArticleDTO> getAllArticles(int pageNo, int pageSize, String sortBy, String sortDirection) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Article> articlePage = articleRepository.findAll(pageable);

        return articlePage.stream().map(Article::articleDTOResponse).collect(Collectors.toList());

    }

    @Override
    public ArticleDTO getArticleByArticleId(Long id) {
        return articleRepository.findById(id).map(Article::articleDTOResponse).orElseThrow();
    }

    @Override
    public void deleteArticleByArticleId(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public void updateArticleByArticleId(Long id, ArticleRequest articleRequest) {
        articleRepository.updateArticleByArticleId(id, articleRequest.getTitle(), articleRequest.getDescription());
    }

    @Override
    public void postCommentByArticleId(CommentRequest commentRequest, Long id, Long userId) {
        articleRepository.postCommentOnArticle(commentRequest.getComment(), id, userId);
    }

//    @Override
//    public List<UserCommentDTO> getCommentsByArticleId(Long id) {
//        return articleRepository;
//    }

//    @Override
//    public List<CommentDTO> getCommentsByArticleId(Long id) {
//        return commentRepository.findCommentsByArticle_ArticleId(id).stream().map(Comment::commentDTOResponse).collect(Collectors.toList());
//    }

    @Override
    public void updatePostedComment(String cmt, Long cmtId, Long articleId, Long userId) {
        commentRepository.updateComment(cmt, cmtId, articleId, userId);
    }

}
