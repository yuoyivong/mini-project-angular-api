package com.example.library_management.model.request;

import com.example.library_management.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequest {
    private String bookTitle;
    private String author;
    private List<Integer> categoryId;
    private String description;
    private String image;

}