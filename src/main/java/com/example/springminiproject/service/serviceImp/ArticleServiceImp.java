package com.example.springminiproject.service.serviceImp;

import com.example.springminiproject.config.GlobalCurrentUserConfig;
import com.example.springminiproject.exception.AccessDeniedException;
import com.example.springminiproject.exception.BlankFieldException;
import com.example.springminiproject.exception.NotFoundException;
import com.example.springminiproject.model.Article;
import com.example.springminiproject.model.Category;
import com.example.springminiproject.model.CategoryArticle;
import com.example.springminiproject.model.User;
import com.example.springminiproject.repository.ArticleRepository;
import com.example.springminiproject.repository.CategoryArticleRepository;
import com.example.springminiproject.repository.CategoryRepository;
import com.example.springminiproject.repository.CommentRepository;
import com.example.springminiproject.request.ArticleRequest;
import com.example.springminiproject.request.CommentRequest;
import com.example.springminiproject.response.dto.ArticleDTO;
import com.example.springminiproject.response.dto.UserDTO;
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
    private final CategoryRepository categoryRepository;
    private final CategoryArticleRepository categoryArticleRepository;

    public ArticleServiceImp(ArticleRepository articleRepository, CategoryRepository categoryRepository, CategoryArticleRepository categoryArticleRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.categoryArticleRepository = categoryArticleRepository;
    }

    @Override
    public ArticleDTO insertNewArticle(ArticleRequest articleRequest, UserDTO user) throws Exception {
//        Long userId = currentUserConfig.getCurrentUserInformation().getUserId();

//        User user = u
        checkExistCategoryId(articleRequest.getCategoryId(), user.getUserId());
        notAllowEmptyTitle(articleRequest.getTitle());

        try {

            User u = new User();
            u.setUserId(user.getUserId());
            u.setUsername(user.getUsername());
            u.setEmail(user.getEmail());
            u.setAddress(user.getAddress());
            u.setPhoneNumber(user.getPhoneNumber());
            u.setCreatedAt(user.getCreatedAt());
            u.setUpdatedAt(user.getUpdatedAt());
            u.setRole(user.getRole());

            Article article = new Article();
            article.setTitle(articleRequest.getTitle());
            article.setDescription(articleRequest.getDescription());
            article.setCreatedAt(LocalDateTime.now());
            article.setUser(u);

            for(Integer i: articleRequest.getCategoryId()) {
                Category category = categoryRepository.findByCategoryIdAndUser_UserId(Long.valueOf(i), user.getUserId())
                        .orElseThrow(() -> new NotFoundException("Category not found."));

                CategoryArticle categoryArticle = new CategoryArticle();
                categoryArticle.setArticle(article);
                categoryArticle.setCategory(category);
                categoryArticle.setCreatedAt(LocalDateTime.now());

                categoryArticleRepository.save(categoryArticle);
            }

            return articleRepository.save(article).articleDTOResponse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

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
    public void deleteArticleByArticleId(Long id, Long userId) {

        isOwnerOfArticle(id, userId);

        try {
            articleRepository.deleteArticleByArticleIdAndUser_UserId(id, userId);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }

    }

    @Override
    public void updateArticleByArticleId(Long id, ArticleRequest articleRequest, Long userId) {
//        Long userId = currentUserConfig.getCurrentUserInformation().getUserId();
        isOwnerOfArticle(id, userId);
        notAllowEmptyTitle(articleRequest.getTitle());
        checkExistCategoryId(articleRequest.getCategoryId(), userId);

        try {
            articleRepository.updateArticleByArticleId(id, articleRequest.getTitle(), articleRequest.getDescription(), userId);
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

//    @Override
//    public void updatePostedComment(Long cmtId, Long userId, String cmt) {
//
//        commentRepository.updateComment(cmtId, userId, cmt);
//
//    }

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

    private void isOwnerOfArticle(Long articleId, Long userId) {
        if(articleRepository.findArticleByArticleIdAndUser_UserId(articleId, userId).isEmpty()) {
            throw new AccessDeniedException("Cannot delete/update not found article id " + articleId);
        }
    }
}
