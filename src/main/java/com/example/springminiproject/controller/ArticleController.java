package com.example.springminiproject.controller;

import com.example.springminiproject.config.GlobalCurrentUserConfig;
import com.example.springminiproject.model.Article;
import com.example.springminiproject.request.ArticleRequest;
import com.example.springminiproject.request.CommentRequest;
import com.example.springminiproject.response.ApiResponse;
import com.example.springminiproject.response.dto.ArticleDTO;
import com.example.springminiproject.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearerAuth")
public class ArticleController {
    private final ArticleService articleService;
    private final GlobalCurrentUserConfig currentUserConfig;

    public ArticleController(ArticleService articleService, GlobalCurrentUserConfig currentUserConfig) {
        this.articleService = articleService;
        this.currentUserConfig = currentUserConfig;
    }

//    create a new article
    @PostMapping("/author/article")
    @Operation(summary = "Create a new article")
    public ResponseEntity<ApiResponse<ArticleDTO>> insertNewArticle(@RequestBody ArticleRequest articleRequest) {
        ApiResponse<ArticleDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.CREATED);
        apiResponse.setMessage("A new article is created successfully.");
        apiResponse.setPayload(articleService.insertNewArticle(articleRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

//    get all articles
    @GetMapping("/article/all")
    @Operation(summary = "Get all available articles")
    public ResponseEntity<ApiResponse<List<ArticleDTO>>> getAllArticles(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "articleId") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    ) {
        List<ArticleDTO> articleDTOList = articleService.getAllArticles(pageNo, pageSize, sortBy, sortDirection);
        ApiResponse<List<ArticleDTO>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get all articles successfully.");
        apiResponse.setPayload(articleDTOList);

        return ResponseEntity.ok().body(apiResponse);

    }

//    get article by id
    @GetMapping("/article/{id}")
    @Operation(summary = "Get article by id")
    public ResponseEntity<ApiResponse<ArticleDTO>> getArticleById(@PathVariable Long id) {
        ApiResponse<ArticleDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get article with id " + id + " successfully.");
        apiResponse.setPayload(articleService.getArticleByArticleId(id));

        return ResponseEntity.ok().body(apiResponse);
    }

//    delete article by id
    @DeleteMapping("/author/article/{id}")
    @Operation(summary = "Delete article by id")
    public ResponseEntity<ApiResponse<Article>> deleteArticleById(@PathVariable Long id) {
        articleService.deleteArticleByArticleId(id);

        ApiResponse<Article> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Delete article id " + id + " successfully.");

        return ResponseEntity.ok().body(apiResponse);

    }

//    update article by id
    @PutMapping("/author/article/{id}")
    @Operation(summary = "Edit article by id")
    public ResponseEntity<ApiResponse<ArticleDTO>> updateArticleById(@PathVariable Long id, @RequestBody ArticleRequest articleRequest) {
        articleService.updateArticleByArticleId(id, articleRequest);

        ApiResponse<ArticleDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Update article with id " + id + " successfully.");
        apiResponse.setPayload(articleService.getArticleByArticleId(id));

        return ResponseEntity.ok().body(apiResponse);
    }

//    add comment via article id and user id
    @PostMapping("/article/{id}/comment")
    @Operation(summary = "Post a comment on any article via its id")
    public ResponseEntity<ApiResponse<ArticleDTO>> postCommentOnArticle(@RequestBody CommentRequest cmtRequest, @PathVariable Long id) {
        Long userId = currentUserConfig.getCurrentUserInformation().getUserId();
        articleService.postCommentByArticleId(cmtRequest, id, userId);
        ApiResponse<ArticleDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.CREATED);
        apiResponse.setMessage("A new comment is posted on article " + id + " by user " + id);
        apiResponse.setPayload(articleService.getArticleByArticleId(id));
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

//    @GetMapping("/{id}/comment")
//    public ResponseEntity<ApiResponse<List<CommentDTO>>> getCommentOnArticle(@PathVariable Long id) {
//        List<CommentDTO> commentDTOList = articleService.getCommentsByArticleId(id);
//        ApiResponse<List<CommentDTO>> apiResponse = new ApiResponse<>();
//        apiResponse.setStatus(HttpStatus.OK);
//        apiResponse.setMessage("Get all comments on article id " + id + " successfully.");
//        apiResponse.setPayload(commentDTOList);
//        return ResponseEntity.ok().body(apiResponse);
//    }

    @GetMapping("/article/{id}/comment")
    @Operation(summary = "Get comments on any article")
    public ResponseEntity<ApiResponse<ArticleDTO>> getCommentOnArticle(@PathVariable Long id) {
        ArticleDTO article = articleService.getArticleByArticleId(id);

        ApiResponse<ArticleDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get all comments on article id " + id + " successfully.");
        apiResponse.setPayload(article);
        return ResponseEntity.ok().body(apiResponse);
    }

//    update posted comment
//    @PutMapping("/article/{id}/comment")
//    public ResponseEntity<ApiResponse<ArticleDTO>> updatePostedComment(
//            @PathVariable("id") Long cmtId,
//            @RequestParam Long articleId,
//            @RequestParam Long userId,
//            @RequestBody CommentRequest commentRequest) {
//        articleService.updatePostedComment(cmtId, articleId, userId, commentRequest.getComment());
//        ApiResponse<ArticleDTO> apiResponse = new ApiResponse<>();
//        apiResponse.setStatus(HttpStatus.OK);
//        apiResponse.setMessage("Comment id " + cmtId + " is updated successfully.");
//        apiResponse.setPayload(articleService.getArticleByArticleId(articleId));
//        return ResponseEntity.ok().body(apiResponse);
//    }

//    private UserDTO getCurrentUserInformation() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) auth.getPrincipal();
//        return userService.findUserByEmail(userDetails.getUsername());
//    }
}
