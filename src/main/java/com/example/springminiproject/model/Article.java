package com.example.springminiproject.model;

import com.example.springminiproject.response.dto.ArticleDTO;
import com.example.springminiproject.response.dto.CommentDTO;
import com.example.springminiproject.response.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<CategoryArticle> categoryArticleList = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user = new User();

    public ArticleDTO articleDTOResponse() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setArticleId(this.articleId);
        articleDTO.setTitle(this.title);
        articleDTO.setDescription(this.description);
        articleDTO.setCreatedAt(this.createdAt);
        articleDTO.setUpdatedAt(this.updatedAt);
        articleDTO.setOwnerOfArticle(this.user.getUserId());

        List<Long> categoryIdList = this.categoryArticleList.stream()
                .map(ca -> ca.getCategory().getCategoryId())
                .collect(Collectors.toList());
        System.out.println("Hello : " + categoryIdList);

        articleDTO.setCategoryIdList(categoryIdList);

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

        return articleDTO;

    }
}
