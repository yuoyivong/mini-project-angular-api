package com.example.library_management.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Book {
    @Id
//    when the id field in the database is type "serial",
//    you don't have to add @GenerateValue here
    private Integer book_id;

    @NotBlank
    private String bookTitle;

    @NotBlank
    private String description;

    @NotBlank
    private String author;

    private String image;

}
