package com.example.springminiproject.controller;

import com.example.springminiproject.request.CommentRequest;
import com.example.springminiproject.response.ApiResponse;
import com.example.springminiproject.response.dto.CommentDTO;
import com.example.springminiproject.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentService commentService;

//    GlobalCurrentUser globalCurrentUser = new GlobalCurrentUser(userService);

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentDTO>> getCommentById(@PathVariable Long id) {
        ApiResponse<CommentDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get comment id " + id + " successfully.");
        apiResponse.setPayload(commentService.getCommentByCommentId(id));
        return ResponseEntity.ok().body(apiResponse);
    }

//    delete comment by id
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentDTO>> deleteCommentById(@PathVariable Long id) {
        ApiResponse<CommentDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Delete comment id " + id + " successfully.");
        return ResponseEntity.ok().body(apiResponse);
    }

    //    update comment by id
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentDTO>> updateCommentById(@PathVariable Long id, @RequestParam Long articleId, @RequestBody CommentRequest commentRequest) {
//        Long userId = globalCurrentUser.getCurrentUserInformation().getUserId();
//        commentService.updateCommentByCommentId(id, articleId, userId, commentRequest);

        ApiResponse<CommentDTO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Update comment id " + id + " successfully.");
        apiResponse.setPayload(commentService.getCommentByCommentId(id));
        return ResponseEntity.ok().body(apiResponse);
    }

}
