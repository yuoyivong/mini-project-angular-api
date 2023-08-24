package com.example.library_management.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequest {
    private String bookTitle;
    private String author;
//    private Integer categoryId;
    private String description;
    private String image;

}