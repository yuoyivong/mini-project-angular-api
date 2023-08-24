package com.example.library_management.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "book")
public class Book {
    @Id
//    when the id field in the database is type "serial",
//    you don't have to add @GenerateValue here
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer book_id;

    @NotBlank
    @Column(name = "book_title")
    private String bookTitle;

    @NotBlank
    private String description;

    @NotBlank
    private String author;

    private String image;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_details",
            joinColumns = {
                    @JoinColumn(name = "book_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "category_id")
            }
    )
    private List<Category> categoryList;

    public void addCategory(Category category) {
        this.categoryList.add(category);
        category.getBooks().add(this);
    }

    public void removeCategory(Integer categoryId) {
        Category category = this.categoryList.stream()
                .filter(cat -> cat.getCategoryId() == categoryId)
                .findFirst().orElse(null);
        if(category != null) {
            this.categoryList.remove(category);
            category.getBooks().remove(this);
        }
    }


}