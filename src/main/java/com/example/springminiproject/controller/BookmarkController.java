package com.example.springminiproject.controller;

import com.example.springminiproject.config.GlobalCurrentUserConfig;
import com.example.springminiproject.model.Article;
import com.example.springminiproject.model.Bookmark;
import com.example.springminiproject.response.ApiResponse;
import com.example.springminiproject.response.dto.ArticleDTO;
import com.example.springminiproject.service.ArticleService;
import com.example.springminiproject.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookmark")
@SecurityRequirement(name = "bearerAuth")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final ArticleService articleService;
    private final GlobalCurrentUserConfig currentUserConfig;

    public BookmarkController(BookmarkService bookmarkService, ArticleService articleService, GlobalCurrentUserConfig currentUserConfig) {
        this.bookmarkService = bookmarkService;
        this.articleService = articleService;
        this.currentUserConfig = currentUserConfig;
    }

    @PostMapping("/{id}")
    @Operation(summary = "Add bookmark on any article")
    public ResponseEntity<ApiResponse<Bookmark>> addBookmarkOnArticle(@PathVariable("id") Long articleId) throws Exception {
        ArticleDTO article = articleService.getArticleByArticleId(articleId);

        Long userId = currentUserConfig.getCurrentUserInformation().getUserId();
        bookmarkService.addBookmarkOnArticle(articleId, userId);
        ApiResponse<Bookmark> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("An article id " + articleId + " is bookmarked successfully.");
        apiResponse.setStatus(HttpStatus.OK);

        return ResponseEntity.ok().body(apiResponse);
    }

//    update bookmark status to false - unmark
    @PutMapping("/{id}")
    @Operation(summary = "Change status when this article is no longer your favorite")
    public ResponseEntity<ApiResponse<Bookmark>> unBookmarkOnArticle(@PathVariable("id") Long articleId) throws Exception {
        ArticleDTO article = articleService.getArticleByArticleId(articleId);

        Long userId = currentUserConfig.getCurrentUserInformation().getUserId();
        bookmarkService.unBookmarkArticle(articleId, userId);
        ApiResponse<Bookmark> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("An article id " + articleId + " is unmarked successfully.");
        apiResponse.setStatus(HttpStatus.OK);

        return ResponseEntity.ok().body(apiResponse);
    }

//    get all bookmark articles of current user
    @GetMapping
    @Operation(summary = "Get a list of bookmarked articles")
    public ResponseEntity<ApiResponse<List<ArticleDTO>>> getAllBookmarkOnArticles(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "articleId") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    ) {

        Long userId = currentUserConfig.getCurrentUserInformation().getUserId();
        List<ArticleDTO> articleDTOList = bookmarkService.getAllBookmarkArticlesByUserId(userId, pageNo, pageSize, sortBy, sortDirection);

        ApiResponse<List<ArticleDTO>> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Get all bookmarked articles successfully.");
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setPayload(articleDTOList);

        return ResponseEntity.ok().body(apiResponse);
    }

}
