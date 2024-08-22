package com.example.springminiproject.service.serviceImp;

import com.example.springminiproject.config.GlobalCurrentUserConfig;
import com.example.springminiproject.exception.BlankFieldException;
import com.example.springminiproject.exception.NotFoundException;
import com.example.springminiproject.model.Article;
import com.example.springminiproject.model.Category;
import com.example.springminiproject.model.CategoryArticle;
import com.example.springminiproject.repository.ArticleRepository;
import com.example.springminiproject.repository.CategoryArticleRepository;
import com.example.springminiproject.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;
    private final GlobalCurrentUserConfig currentUserConfig;
    private final CategoryArticleRepository categoryArticleRepository;

    public ArticleServiceImp(ArticleRepository articleRepository, CommentRepository commentRepository, CategoryRepository categoryRepository, GlobalCurrentUserConfig currentUserConfig, CategoryArticleRepository categoryArticleRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.categoryRepository = categoryRepository;
        this.currentUserConfig = currentUserConfig;
        this.categoryArticleRepository = categoryArticleRepository;
    }

    @Override
    public ArticleDTO insertNewArticle(ArticleRequest articleRequest) {
        Long userId = currentUserConfig.getCurrentUserInformation().getUserId();

        checkExistCategoryId(articleRequest.getCategoryId(), userId);
        notAllowEmptyTitle(articleRequest.getTitle());

        Article article = new Article();
        article.setTitle(articleRequest.getTitle());
        article.setDescription(articleRequest.getDescription());
        article.setCreatedAt(LocalDateTime.now());

        for(Integer i: articleRequest.getCategoryId()) {
            Category category = categoryRepository.findByCategoryIdAndUser_UserId(Long.valueOf(i), userId)
                    .orElseThrow(() -> new NotFoundException("Category not found."));

            CategoryArticle categoryArticle = new CategoryArticle();
            categoryArticle.setArticle(article);
            categoryArticle.setCategory(category);
            categoryArticle.setCreatedAt(LocalDateTime.now());

            categoryArticleRepository.save(categoryArticle);
        }

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
    public ArticleDTO getArticleByArticleId(Long id) throws Exception {
        checkExistArticleId(id);

        try {
            return articleRepository.findById(id).map(Article::articleDTOResponse).orElseThrow();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public void deleteArticleByArticleId(Long id) {

        checkExistArticleId(id);

        try {
            articleRepository.deleteById(id);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }

    }

    @Override
    public void updateArticleByArticleId(Long id, ArticleRequest articleRequest) {
        Long userId = currentUserConfig.getCurrentUserInformation().getUserId();
        checkExistArticleId(id);
        notAllowEmptyTitle(articleRequest.getTitle());
        checkExistCategoryId(articleRequest.getCategoryId(), userId);

        try {
            articleRepository.updateArticleByArticleId(id, articleRequest.getTitle(), articleRequest.getDescription());
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }

    }

    @Override
    public void postCommentByArticleId(CommentRequest commentRequest, Long id, Long userId) {
        checkExistArticleId(id);

        try {
            if(!commentRequest.getComment().isBlank()) {
                articleRepository.postCommentOnArticle(commentRequest.getComment(), id, userId);
            }
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }

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
    public void updatePostedComment(Long cmtId, Long articleId, Long userId, String cmt) {

        commentRepository.updateComment(cmtId, articleId, userId, cmt);

    }

//    check whether the article id exists or not
    private void checkExistArticleId(Long id) {

        if(articleRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Article id " + id + " not found.");
        }

    }

//    check whether category id exits or not
    private void checkExistCategoryId(List<Integer> categoryIdList, Long userId) {

        for(Integer i: categoryIdList) {
            if(categoryRepository.findByCategoryIdAndUser_UserId(Long.valueOf(i), userId).isEmpty()) {
                throw new NotFoundException("Category id " + i + " not found");
            }
        }

    }

    private void notAllowEmptyTitle(String title) {
        if(title.isBlank()) {
            throw new BlankFieldException("Title cannot be empty.");
        }
    }
}
