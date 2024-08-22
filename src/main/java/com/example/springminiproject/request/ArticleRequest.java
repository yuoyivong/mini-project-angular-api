package com.example.springminiproject.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticleRequest {

    private String title;
    private String description;
    private List<Integer> categoryId;

}
