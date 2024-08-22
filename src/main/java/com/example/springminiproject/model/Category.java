package com.example.springminiproject.model;

import com.example.springminiproject.response.dto.ArticleDTO;
import com.example.springminiproject.response.dto.CategoryDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name", nullable = false, unique = true)
    private String categoryName;

    @Column(name = "amount_of_articles")
    private Integer amountOfArticles;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<CategoryArticle> categoryArticleList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CategoryDTO categoryDTOResponse() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(this.categoryId);
        categoryDTO.setCategoryName(this.categoryName);
        categoryDTO.setAmountOfArticles(this.amountOfArticles);
        categoryDTO.setCreatedAt(this.createdAt);
        categoryDTO.setUpdatedAt(this.updatedAt);

        List<ArticleDTO> articleDTOList = new ArrayList<>();
        for(CategoryArticle c : this.categoryArticleList) {
            Article a = c.getArticle();
            articleDTOList.add(new ArticleDTO(
                    a.getArticleId(),
                    a.getTitle(),
                    a.getDescription(),
                    a.getCreatedAt(),
                    a.getUpdatedAt(),
                    a.articleDTOResponse().getCommentList()
            ));
        }

        categoryDTO.setArticleList(articleDTOList);

        return categoryDTO;

    }
}
