package com.example.springminiproject.model;

import com.example.springminiproject.response.dto.CommentDTO;
import com.example.springminiproject.response.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(nullable = false)
    private String cmt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CommentDTO commentDTOResponse() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentId(this.commentId);
        commentDTO.setComment(this.cmt);
        commentDTO.setCreatedAt(this.createdAt);
        commentDTO.setUpdatedAt(this.updatedAt);

        commentDTO.setUser(this.user.userDTOResponse());

        return commentDTO;
    }
}
