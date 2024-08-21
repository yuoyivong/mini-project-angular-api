package com.example.springminiproject.model;

import com.example.springminiproject.response.dto.CategoryDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<CategoryArticle> categoryList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "category")
    private List<Bookmark> bookmarkList;

    public CategoryDTO categoryDTOResponse() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(this.categoryId);
        categoryDTO.setCategoryName(this.categoryName);
        categoryDTO.setCreatedAt(this.createdAt);
        categoryDTO.setUpdatedAt(this.updatedAt);

        return categoryDTO;

    }
}
