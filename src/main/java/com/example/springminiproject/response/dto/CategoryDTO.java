package com.example.springminiproject.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    private Long categoryId;
    private String categoryName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer amountOfArticles;

    private LocalDateTime createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedAt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ArticleDTO> articleList;

    public CategoryDTO() {
        this.amountOfArticles = null;
    }

}
