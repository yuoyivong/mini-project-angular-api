package com.example.springminiproject.controller;

import com.example.springminiproject.config.GlobalCurrentUserConfig;
import com.example.springminiproject.request.CommentRequest;
import com.example.springminiproject.response.ApiResponse;
import com.example.springminiproject.response.dto.CommentDTO;
import com.example.springminiproject.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@SecurityRequirement(name = "bearerAuth")
public class CommentController {
    private final CommentService commentService;

    private final GlobalCurrentUserConfig globalCurrentUser;

    public CommentController(CommentService commentService, GlobalCurrentUserConfig globalCurrentUser) {
        this.commentService = commentService;
        this.globalCurrentUser = globalCurrentUser;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get comment by its id")
    public ResponseEntity<ApiResponse<CommentDTO>> getCommentById(@PathVariable Long id) {
        Long userId = globalCurrentUser.getCurrentUserInformation().getUserId();
        ApiResponse<CommentDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get comment id " + id + " successfully.");
        apiResponse.setPayload(commentService.getCommentOnArticleById(id, userId));
        return ResponseEntity.ok().body(apiResponse);
    }

//    delete comment by id
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete comment its by id")
    public ResponseEntity<ApiResponse<CommentDTO>> deleteCommentById(@PathVariable Long id) {
        Long userId = globalCurrentUser.getCurrentUserInformation().getUserId();
        commentService.deleteCommentById(id, userId);

        ApiResponse<CommentDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Delete comment id " + id + " successfully.");
        return ResponseEntity.ok().body(apiResponse);
    }

    //    update comment by id
    @PutMapping("/{id}")
    @Operation(summary = "Edit comment by its id")
    public ResponseEntity<ApiResponse<CommentDTO>> updateCommentById(@PathVariable Long id, @RequestBody CommentRequest commentRequest) {
        Long userId = globalCurrentUser.getCurrentUserInformation().getUserId();
        commentService.updateCommentByCommentId(id, userId, commentRequest);

        ApiResponse<CommentDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Update comment id " + id + " successfully.");
        apiResponse.setPayload(commentService.getCommentOnArticleById(id, userId));
        return ResponseEntity.ok().body(apiResponse);
    }

}
