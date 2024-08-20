package com.example.springminiproject.model;

import com.example.springminiproject.response.dto.ArticleDTO;
import com.example.springminiproject.response.dto.CommentDTO;
import com.example.springminiproject.response.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long articleId;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
    private List<CategoryArticle> articleList;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    private List<Bookmark> bookmarkList = new ArrayList<>();

    public ArticleDTO articleDTOResponse() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setArticleId(this.articleId);
        articleDTO.setTitle(this.title);
        articleDTO.setDescription(this.description);
        articleDTO.setCreatedAt(this.createdAt);
        articleDTO.setUpdatedAt(this.updatedAt);

        Set<CommentDTO> commentDTOSet = new HashSet<>();

        for(Comment c : this.commentList) {
            commentDTOSet.add(new CommentDTO(
                    c.getCommentId(),
                    c.getCmt(),
                    c.getCreatedAt(),
                    c.getUpdatedAt(),
                    c.commentDTOResponse().getUser()
            ));
        }

        articleDTO.setCommentList(new ArrayList<>(commentDTOSet));


//        List<UserCommentDTO> userCommentDTOList = new ArrayList<>();
//        for (Comment c : this.commentList) {
//            userCommentDTOList.add(new CommentDTO(c.getCommentId(), c.getCmt(), c.getCreatedAt(), c.getUpdatedAt()));
//        }

//        articleDTO.setCommentList(commentDTOList);

//        Map<Long, UserCommentDTO> userMap = new HashMap<>();
//
//        for (Comment c : this.commentList) {
//            User user = c.getUser();
//
//            UserCommentDTO userCommentDTO = userMap.computeIfAbsent(user.getUserId(), id -> {
//                UserCommentDTO newUserDTO = new UserCommentDTO();
//                newUserDTO.setUserId(user.getUserId());
//                newUserDTO.setUsername(user.getUsername());
//                newUserDTO.setEmail(user.getEmail());
//                newUserDTO.setAddress(user.getAddress());
//                newUserDTO.setPhoneNumber(user.getPhoneNumber());
//                newUserDTO.setCreatedAt(user.getCreatedAt());
//                newUserDTO.setUpdatedAt(user.getUpdatedAt());
//                newUserDTO.setCommentList(new ArrayList<>());
//                return newUserDTO;
//            });
//
//            userCommentDTO.getCommentList().add(new CommentDTO(c.getCommentId(), c.getCmt(), c.getCreatedAt(), c.getUpdatedAt()));
//
//        }
//
//        articleDTO.setUserCommentList(new ArrayList<>(userMap.values()));

        return articleDTO;

    }
}
