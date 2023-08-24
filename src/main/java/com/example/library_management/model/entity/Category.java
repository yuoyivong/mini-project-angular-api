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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer category_id;

    @NotBlank
    @Column(name = "category_name")
    private String categoryName;

    @ManyToMany(mappedBy = "categoryList", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Book> bookList;

}