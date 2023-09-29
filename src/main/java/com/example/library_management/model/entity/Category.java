package com.example.library_management.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @NotBlank
    @Column(name = "category_name")
    private String categoryName;

    @ManyToMany(mappedBy = "categoryList")
    @JsonIgnore
    private List<Book> bookList;

//    public List<Book> getBooks() {
//        return bookList;
//    }
//
//    public void setBooks(List<Book> books) {
//        this.bookList = books;
//    }


}